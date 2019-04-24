package com.example.demo.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.formcommand.CategoryCommand;
import com.example.demo.formcommand.IngredientCommand;
import com.example.demo.formcommand.RecipeCommand;
import com.example.demo.model.Category;
import com.example.demo.model.Comment;
import com.example.demo.model.Ingredient;
import com.example.demo.model.Recipe;

import lombok.Synchronized;


@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand>{

    private final CategoryToCategoryCommand categoryConveter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final CommentToCommentCommand commentConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConveter, IngredientToIngredientCommand ingredientConverter,
                                 CommentToCommentCommand notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.commentConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        final RecipeCommand commandRecipe = new RecipeCommand();
        commandRecipe.setId(recipe.getId());
        commandRecipe.setName(recipe.getName());
        commandRecipe.setCookTime(recipe.getCookTime());
        commandRecipe.setPrepTime(recipe.getPrepTime());
        commandRecipe.setDifficulty(recipe.getDifficulty());
        commandRecipe.setContent(recipe.getContent());
        commandRecipe.setServings(recipe.getServings());
        
        if (recipe.getComments() != null && recipe.getComments().size() > 0){
            Set<Comment> comments = recipe.getComments();
            comments.forEach(
                    		(Comment comment) -> commandRecipe.getComments().add(
                    				commentConverter.convert(comment)));
        }
        
        if (recipe.getCategories() != null && recipe.getCategories().size() > 0){
        	ArrayList<CategoryCommand> commandCategoryList = new ArrayList<CategoryCommand>();
        	for (Category category : recipe.getCategories()) {
        		CategoryCommand commandCategory = categoryConveter.convert(category);
        		commandCategoryList.add(commandCategory);
 			}
        	commandRecipe.setCategories(commandCategoryList);
        	
        }

        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0){
        	List<IngredientCommand> commandIngredientList = new ArrayList<IngredientCommand>();
        	for (Ingredient ingredient : recipe.getIngredients()) {
        		IngredientCommand commandCategory = ingredientConverter.convert(ingredient);
        		commandIngredientList.add(commandCategory);
 			}
        	commandRecipe.setIngredients(commandIngredientList);
        	
        }

        return commandRecipe;
    }
    
    @Synchronized
	public RecipeCommand convert(Recipe recipe, List<CategoryCommand> commandCategory) {
    	RecipeCommand commandRecipe = this.convert(recipe);
		Map<String, CategoryCommand> mapCategory = new HashMap<String, CategoryCommand>();

    	for (CategoryCommand commandCat: commandCategory) {
    		commandCat.setChecked(false);
    		mapCategory.put(commandCat.getDescription(), commandCat);
		}
    	//erase category with checked categories  = categoryies already saved in bdd
    	List<CategoryCommand> categoryArraybeforeMerge = commandRecipe.getCategories();
    	for (CategoryCommand categoryCommand : categoryArraybeforeMerge) {
    		String key = categoryCommand.getDescription();
    		CategoryCommand value = categoryCommand;
    		value.setChecked(true);
    		mapCategory.put(key, value);
		}
    	
    	List<CategoryCommand> categories = (List<CategoryCommand>) mapCategory.values();
    	commandRecipe.setCategories(categories);
    	return commandRecipe;
	}
}
