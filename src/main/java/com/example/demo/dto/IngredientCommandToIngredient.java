package com.example.demo.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.formcommand.IngredientCommand;
import com.example.demo.model.Ingredient;

import lombok.Synchronized;


@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	private final StringToUomConverters stringToUomConverters;
	
    public IngredientCommandToIngredient(StringToUomConverters stringToUomConverters) {
    	this.stringToUomConverters = stringToUomConverters;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setName(source.getName());
        ingredient.setUom(stringToUomConverters.convert(source.getUom()) );
        return ingredient;
    }
}
