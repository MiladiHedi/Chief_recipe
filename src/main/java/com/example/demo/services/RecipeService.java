package com.example.demo.services;

import java.util.Set;

import com.example.demo.entities.Recipe;
import com.example.demo.form.RecipeCommand;

public interface RecipeService {

    Set<RecipeCommand> getRecipes();

    Recipe findById(long id);
    
	RecipeCommand findRecipeCommandById(long id);
        
	RecipeCommand saveCommandRecipe(RecipeCommand recipeCommand);

	void deleteById(long id);

	RecipeCommand addEmptyIngredientToForm(RecipeCommand recipeCommand);

	RecipeCommand removeIngredient(RecipeCommand recipeCommand, long ingredientId);

	RecipeCommand getEmptyRecipeCommandWithAllCategories();
	
}
