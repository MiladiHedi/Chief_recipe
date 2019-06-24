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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.CommentCommand;
import com.example.demo.form.RecipeCommand;
import com.example.demo.services.CategoryService;
import com.example.demo.services.CommentService;
import com.example.demo.services.RecipeService;

@Controller
public class RecipeController {
	
	private static final  Logger log = LoggerFactory.getLogger(RecipeController.class);

	private static final String RECIPE_FORM_TEMPLATE = "recipe/form";
	private static final String RECIPE_SHOW_TEMPLATE = "recipe/show";
	
    private final RecipeService recipeService;
	private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, 
    		CategoryService categoryService, CommentService commentService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    /**
     * request to get show page
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value ="/recipe/{id}/show", method = RequestMethod.GET)
    public String showById(@PathVariable String id, Model model){
    	

    		long recipeId = Long.parseLong(id);
    		RecipeCommand recipeCommand = recipeService.findRecipeCommandById(recipeId);
        	model.addAttribute("recipe", recipeCommand);
        	model.addAttribute("comment", new CommentCommand());
        	
        	log.debug("id : '"+ id +"'");

        return RECIPE_SHOW_TEMPLATE;
    }

    /**
     * request to get recipe form page for empty recipe
     * @param model
     * @return
     */
    @RequestMapping(value ="/recipe/new" , method = RequestMethod.GET)
    public String recipeForm(Model model) {
    	
    	RecipeCommand commandRecipe = recipeService.getEmptyRecipeCommandWithAllCategories();
        model.addAttribute("recipe",commandRecipe);
        
        return RECIPE_FORM_TEMPLATE;
    }
    
    /**
     * request to get recipe form page to update a recipe
     * @param model
     * @return
     */
    @RequestMapping(value = "/recipe/{id}/update", method = RequestMethod.GET)
    public String updateRecipe(@PathVariable String id, Model model) {
    	long recipeId = Long.parseLong(id);
    	RecipeCommand commandRecipe = recipeService.findRecipeCommandById(recipeId);
    	model.addAttribute("recipe", commandRecipe);
    	log.debug("recipeBean : '"+ commandRecipe.toString()+"'");
    	return RECIPE_FORM_TEMPLATE;
    }
    
    /**
     * request to save a recipe
     * @param recipeCommand
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping(value="/recipe")
    public String saveRecipe(@Valid @ModelAttribute("recipe")  RecipeCommand recipeCommand, BindingResult bindingResult, Model model) {
    	
     	if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
            	log.debug(objectError.toString());
                log.debug(objectError.toString());
            });
            recipeCommand.setCategories(this.categoryService.findCompleteCategoriesCommandByRecipeId(recipeCommand.getId()));
            return RECIPE_FORM_TEMPLATE;
        }
    	log.debug( "recipe bean:"+recipeCommand);
    	RecipeCommand recipe = recipeService.saveCommandRecipe(recipeCommand);
    	model.addAttribute("recipe", recipe);
    	model.addAttribute("comment", new CommentCommand());
    	return RECIPE_SHOW_TEMPLATE;
    }
    
    /**
     * request to delete a recipe
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id, Model model){

        log.debug("Deleting recipe id: " + id);
        long recipeId = Long.parseLong(id);
		recipeService.deleteById(recipeId);

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
    
    /**
     * request to add an empty ingredient to form
     * @param recipe
     * @param bindingResult
     * @param model
     * @return
     */
    @RequestMapping(value="/recipe", params={"addIngredient"}, method = RequestMethod.POST)
    public String addIngredient(RecipeCommand recipe,  BindingResult bindingResult, Model model) {
    	RecipeCommand result = this.recipeService.addEmptyIngredientToForm(recipe);
    	result.setCategories(this.categoryService.fillCommandCategories(result.getCategories()));
    	
    	model.addAttribute("recipe", result);
    	return RECIPE_FORM_TEMPLATE;
    }

    /**
     * request to delete an ingredient
     * @param index
     * @param recipe
     * @param model
     * @return
     */
    @RequestMapping(value="/recipe", params={"deleteIngredient"})
    public String deleteIngredientById( @RequestParam("deleteIngredient") long index,
    	RecipeCommand recipe, Model model ){
    	
    	RecipeCommand commandRecipe = this.recipeService.removeIngredient(recipe, index);
   
    	model.addAttribute("recipe", commandRecipe);
        return RECIPE_FORM_TEMPLATE;
    }
    
    
 

    
    
}