package com.example.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Category;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Recipe;
import com.example.demo.form.CategoryCommand;
import com.example.demo.form.IngredientCommand;
import com.example.demo.form.RecipeCommand;

import lombok.Synchronized;


@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final CommentCommandToComment commentConverter;

    

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter, IngredientCommandToIngredient ingredientConverter,
                                 CommentCommandToComment commentConverter) {
        this.categoryConverter = categoryConverter;
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
            	Category category = categoryConverter.convert(commandCategory);
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
