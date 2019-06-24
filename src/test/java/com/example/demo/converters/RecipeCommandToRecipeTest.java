package com.example.demo.converters;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.example.demo.converter.CategoryCommandToCategory;
import com.example.demo.converter.CommentCommandToComment;
import com.example.demo.converter.IngredientCommandToIngredient;
import com.example.demo.converter.RecipeCommandToRecipe;
import com.example.demo.converter.StringToUomConverters;
import com.example.demo.entities.Difficulty;
import com.example.demo.entities.Recipe;
import com.example.demo.form.CategoryCommand;
import com.example.demo.form.CommentCommand;
import com.example.demo.form.IngredientCommand;
import com.example.demo.form.RecipeCommand;

public class RecipeCommandToRecipeTest {
    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String NAME = "My Recipe";
    public static final String PREPARATION_CONTENT = "PREPARATION_CONTENT";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long COMMENT_ID = 9L;
	private static final String COMMENT_CONTENT = "COMMENT_CONTENT";
	private static final String AUTHOR = "AUTHOR";
	private static final String DATE = new Date().toString();

    
    RecipeCommandToRecipe converter;


    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new StringToUomConverters()),
                new CommentCommandToComment());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setName(NAME);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setContent(PREPARATION_CONTENT);
        recipeCommand.setServings(SERVINGS);

        CommentCommand CommentCommand = new CommentCommand();
        CommentCommand.setId(COMMENT_ID);
        CommentCommand.setComContent(COMMENT_CONTENT);
        CommentCommand.setDate(DATE);
        CommentCommand.setAuthor(AUTHOR);
        
        recipeCommand.getComments().add(CommentCommand);

        CategoryCommand category = new CategoryCommand();
        category.setId(CAT_ID_1);

        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID2);

        recipeCommand.getCategories().add(category);
        recipeCommand.getCategories().add(category2);

        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(INGRED_ID_1);

        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGRED_ID_2);

        recipeCommand.getIngredients().add(ingredient);
        recipeCommand.getIngredients().add(ingredient2);

        //when
        Recipe recipe  = converter.convert(recipeCommand);

        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(NAME, recipe.getName());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(PREPARATION_CONTENT, recipe.getContent());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(1, recipe.getComments().size());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }

}