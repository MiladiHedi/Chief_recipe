package com.example.demo.services;

import java.util.Set;

import com.example.demo.formcommand.RecipeCommand;
import com.example.demo.model.Comment;
import com.example.demo.model.Recipe;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);
        
    Recipe saveCommandRecipe(RecipeCommand beanRecipe);
    
	RecipeCommand findRecipeCommandById(Long id);

	void deleteById(Long id);

	RecipeCommand findCompleteCommandRecipeById(Long id);

	RecipeCommand addEmptyIngredient(RecipeCommand recipeBean);

	Recipe addComment(long recipeId, Comment comment);

	RecipeCommand removeIngredient(RecipeCommand recipe, long ingredientId);
	
	
}
