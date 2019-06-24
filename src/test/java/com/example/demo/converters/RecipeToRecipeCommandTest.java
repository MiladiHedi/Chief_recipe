package com.example.demo.converters;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.example.demo.converter.CategoryToCategoryCommand;
import com.example.demo.converter.CommentToCommentCommand;
import com.example.demo.converter.IngredientToIngredientCommand;
import com.example.demo.converter.RecipeToRecipeCommand;
import com.example.demo.entities.Category;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Difficulty;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Recipe;
import com.example.demo.form.RecipeCommand;

public class RecipeToRecipeCommandTest {

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
    
    RecipeToRecipeCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(
                new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(),
                new CommentToCommentCommand());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void convert() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setName(NAME);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setContent(PREPARATION_CONTENT);
        recipe.setServings(SERVINGS);

        Comment comment = new Comment();
        comment.setId(COMMENT_ID);
        comment.setComContent(COMMENT_CONTENT);
        comment.setDate(new Date());
        comment.setAuthor(AUTHOR);

        recipe.addComment(comment);

        Category category = new Category();
        category.setId(CAT_ID_1);

        Category category2 = new Category();
        category2.setId(CAT_ID2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);

        //when
        RecipeCommand command = converter.convert(recipe);

        //then
        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(NAME, command.getName());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(PREPARATION_CONTENT, command.getContent());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(1, command.getComments().size());
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().size());

    }

}