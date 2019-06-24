package com.example.demo.converters;


import org.junit.Before;
import org.junit.Test;

import com.example.demo.converter.IngredientToIngredientCommand;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Recipe;
import com.example.demo.entities.UnitOfMeasure;
import com.example.demo.form.IngredientCommand;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class IngredientToIngredientCommandTest {

    public static final Recipe RECIPE = new Recipe();
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String NAME = "Ingredient";
    public static final Long UOM_ID = 2L;
    public static final Long ID_VALUE = 1L;
	private static final UnitOfMeasure UOM = UnitOfMeasure.CUP;


    IngredientToIngredientCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientToIngredientCommand();
    }

    @Test
    public void testNullConvert() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    public void testConvert() throws Exception {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setRecipe(RECIPE);
        ingredient.setAmount(AMOUNT);
        ingredient.setName(NAME);
        ingredient.setUom(UOM);
        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);
        //then
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertNotNull(ingredientCommand.getUom());
        assertEquals(UOM.getValue(), ingredientCommand.getUom());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(NAME, ingredientCommand.getName());
    }
}