package com.example.demo.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.formcommand.IngredientCommand;
import com.example.demo.model.Ingredient;

import lombok.Synchronized;


@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

   
    public IngredientToIngredientCommand() {
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }

        IngredientCommand commandIngredient = new IngredientCommand();
        commandIngredient.setId(ingredient.getId());
        commandIngredient.setAmount(ingredient.getAmount());
        commandIngredient.setName(ingredient.getName());
        commandIngredient.setUom(ingredient.getUom().getValue());
        return commandIngredient;
    }
}
