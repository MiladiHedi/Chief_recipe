package com.example.demo.services;

import java.util.Set;

import com.example.demo.formcommand.CommentCommand;
import com.example.demo.formcommand.RecipeCommand;
import com.example.demo.model.Recipe;

import javassist.NotFoundException;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(long id) throws NotFoundException;
    
	RecipeCommand findRecipeCommandById(long id);
        
    Recipe saveCommandRecipe(RecipeCommand recipeCommand);

	void deleteById(long id);

	RecipeCommand addEmptyIngredient(RecipeCommand recipeCommand);

	RecipeCommand saveRecipeComment(long recipeId, CommentCommand commentCommand);

	RecipeCommand removeIngredient(RecipeCommand recipeCommand, long ingredientId);

	RecipeCommand getRecipeCommandWithAllCategories();
	
}
