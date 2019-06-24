package com.example.demo.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Category;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Recipe;
import com.example.demo.form.CategoryCommand;
import com.example.demo.form.IngredientCommand;
import com.example.demo.form.RecipeCommand;

import lombok.Synchronized;


@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand>{

    private final CategoryToCategoryCommand categoryConveter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final CommentToCommentCommand commentConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConveter, IngredientToIngredientCommand ingredientConverter,
                                 CommentToCommentCommand commentConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.commentConverter = commentConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
    	if (recipe == null) {
            return null;
        }
        RecipeCommand commandRecipe = new RecipeCommand();
       
        commandRecipe.setId(recipe.getId());
        commandRecipe.setName(recipe.getName());
        commandRecipe.setCookTime(recipe.getCookTime());
        commandRecipe.setPrepTime(recipe.getPrepTime());
        commandRecipe.setDifficulty(recipe.getDifficulty());
        commandRecipe.setContent(recipe.getContent());
        commandRecipe.setServings(recipe.getServings());

        
        if (recipe.getComments() != null && !recipe.getComments().isEmpty()){
            Set<Comment> comments = recipe.getComments();
            comments.forEach(
                    		(Comment comment) -> commandRecipe.getComments().add(
                    				commentConverter.convert(comment)));
        }
        
        if (recipe.getCategories() != null && !recipe.getCategories().isEmpty()){
        	ArrayList<CategoryCommand> commandCategoryList = new ArrayList<>();
        	for (Category category : recipe.getCategories()) {
        		CategoryCommand commandCategory = categoryConveter.convert(category);
        		commandCategory.setChecked(true);

        		commandCategoryList.add(commandCategory);
 			}
        	commandRecipe.setCategories(commandCategoryList);
        	
        }

        if (recipe.getIngredients() != null && !recipe.getIngredients().isEmpty()){
        	List<IngredientCommand> commandIngredientList = new ArrayList<>();
        	for (Ingredient ingredient : recipe.getIngredients()) {
        		IngredientCommand ingredientCommand = ingredientConverter.convert(ingredient);
        		commandIngredientList.add(ingredientCommand);
 			}
        	commandRecipe.setIngredients(commandIngredientList);
        	
        }
        
        return commandRecipe;
    }
    
    @Synchronized
	public RecipeCommand convert(Recipe recipe, List<CategoryCommand> commandCategory) {
    	RecipeCommand commandRecipe = this.convert(recipe);
		Map<String, CategoryCommand> mapCategory = new HashMap<>();

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
