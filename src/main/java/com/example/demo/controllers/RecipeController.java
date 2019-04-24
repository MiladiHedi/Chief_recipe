package com.example.demo.controllers;



import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.formcommand.RecipeCommand;
import com.example.demo.model.Comment;
import com.example.demo.model.Recipe;
import com.example.demo.services.CategoryService;
import com.example.demo.services.RecipeService;

@Controller
public class RecipeController {
	
	private final static Logger log = LoggerFactory.getLogger(RecipeController.class);

	private static final String RECIPE_FORM_URL = "recipe/form";
	private static final String RECIPE_SHOW_URL = "recipe/show";
	
    private final RecipeService recipeService;
	private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
    	Recipe recipe = recipeService.findById( Long.parseLong(id));
    	model.addAttribute("recipe", recipe);
    	model.addAttribute("comment", new Comment());
    	
    	log.debug("id : '"+ recipe.toString()+"'");
        return RECIPE_SHOW_URL;
    }

    @RequestMapping("/recipe/new")
    public String recipeForm(Model model) {
    	RecipeCommand commandRecipe =new RecipeCommand();
    	commandRecipe.setCategories(this.categoryService.findAllCategoryCommand());
        model.addAttribute("recipe",commandRecipe);
        
        return RECIPE_FORM_URL;
    }
  
    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
    	RecipeCommand recipeBean = recipeService.findCompleteCommandRecipeById(Long.parseLong(id));
    	model.addAttribute("recipe", recipeBean);
    	log.debug("recipeBean : '"+ recipeBean.toString()+"'");
    	return RECIPE_FORM_URL;
    }
    
    @PostMapping(value="/recipe")
    public String saveRecipe(@Valid @ModelAttribute("recipe")  RecipeCommand recipeCommand, BindingResult bindingResult, Model model) {
    	
    	if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
            	log.debug(objectError.toString());
                log.debug(objectError.toString());
            });
            recipeCommand.setCategories(this.categoryService.findCompleteCategoriesCommandByRecipeId(recipeCommand.getId()));
            return RECIPE_FORM_URL;
        }
    	System.out.println( "recipe bean:"+recipeCommand);
    	Recipe recipe = recipeService.saveCommandRecipe(recipeCommand);
    	model.addAttribute("recipe", recipe);
    	model.addAttribute("comment", new Comment());
    	return RECIPE_SHOW_URL;
    }
    
    @RequestMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id, Model model){

        log.debug("Deleting recipe id: " + id);
        recipeService.deleteById(Long.valueOf(id));
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
    
    @RequestMapping(value="/recipe", params={"addIngredient"})
    public String addIngredient(RecipeCommand recipe,  BindingResult bindingResult, Model model) {
    	RecipeCommand result = this.recipeService.addEmptyIngredient(recipe);
    	result.setCategories(this.categoryService.fillCommandCategories(result.getCategories()));
    	model.addAttribute("recipe", result);
    	return RECIPE_FORM_URL;
    }

 /*   //@RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    @RequestMapping(value="/recipe", params={"deleteIngredient"})
    public String deleteIngredientById(String idToDelete,
    		RecipeCommand recipe, Model model ){
    	RecipeCommand commandRecipe = null;
    	long ingredientIdL = 0;
    	try {
    		ingredientIdL = Long.parseLong(idToDelete);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
    	
    	if (ingredientIdL < 0) {
			//ingredient not in db we remove ingredient unsaved 
    		for (IngredientCommand ingredient : recipe.getIngredients()) {
				if (ingredient.getId() == ingredientIdL) {
					recipe.getIngredients().remove(ingredient);
				}
			}
		} else {
	    	commandRecipe = this.recipeService.removeIngredient(recipe.getId(), ingredientIdL);

		}
    	model.addAttribute("recipe", commandRecipe);
    	model.addAttribute("newComment", new Comment());
        return RECIPE_FORM_URL;
    }*/
    
  //@RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    @RequestMapping(value="/recipe", params={"deleteIngredient"})
    public String deleteIngredientById( @RequestParam("deleteIngredient") long index,
    	RecipeCommand recipe, Model model ){
    	
    	RecipeCommand commandRecipe = this.recipeService.removeIngredient(recipe, index);
   
    	model.addAttribute("recipe", commandRecipe);
        return RECIPE_FORM_URL;
    }
    
    @PostMapping(value="/recipe/comment")
    public String addComment( Recipe recipe , @Valid  Comment comment, 
    		BindingResult bindingResult, Model model) {
    	long recipeId = recipe.getId();
    	recipe = this.recipeService.findById(recipeId);
    	if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            
            model.addAttribute("recipe", recipe);
        	model.addAttribute("comment", comment);
            return RECIPE_SHOW_URL;
        }
    	
    	
    	Recipe result = this.recipeService.addComment(recipeId, comment);
    	model.addAttribute("recipe", result);
    	model.addAttribute("comment", new Comment());
    	return RECIPE_SHOW_URL;
    }
    
}