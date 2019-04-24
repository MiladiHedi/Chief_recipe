package com.example.demo.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.formcommand.CategoryCommand;
import com.example.demo.formcommand.IngredientCommand;
import com.example.demo.formcommand.RecipeCommand;
import com.example.demo.model.Category;
import com.example.demo.model.Ingredient;
import com.example.demo.model.Recipe;

import lombok.Synchronized;


@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConveter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final CommentCommandToComment commentConverter;

    

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter, IngredientCommandToIngredient ingredientConverter,
                                 CommentCommandToComment commentConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.commentConverter = commentConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand commandRecipe) {
        if (commandRecipe == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(commandRecipe.getId());
        recipe.setCookTime(commandRecipe.getCookTime());
        recipe.setPrepTime(commandRecipe.getPrepTime());
        recipe.setName(commandRecipe.getName());
        recipe.setDifficulty(commandRecipe.getDifficulty());
        recipe.setContent(commandRecipe.getContent());
        recipe.setServings(commandRecipe.getServings());
        
        if (commandRecipe.getComments() != null && commandRecipe.getComments().size() > 0){
            commandRecipe.getComments()
                    .forEach( comment -> recipe.getComments().add(commentConverter.convert(comment)));
        }
        
        if (commandRecipe.getCategories() != null && commandRecipe.getCategories().size() > 0){
            for (CategoryCommand commandCategory : commandRecipe.getCategories()) {
            	Category category = categoryConveter.convert(commandCategory);
            	recipe.getCategories().add(category);
			}
        }

        
  
        if (commandRecipe.getIngredients() != null && !commandRecipe.getIngredients().isEmpty()){
        	for (IngredientCommand commandIngredient : commandRecipe.getIngredients()) {
        		Ingredient ingredient = ingredientConverter.convert(commandIngredient);
        		ingredient.setRecipe(recipe);
            	recipe.getIngredients().add(ingredient);
			}
        	
        }

        return recipe;
    }
}
