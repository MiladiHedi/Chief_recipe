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

import com.example.demo.formcommand.CommentCommand;
import com.example.demo.formcommand.RecipeCommand;
import com.example.demo.model.Comment;
import com.example.demo.model.Recipe;
import com.example.demo.services.CategoryService;
import com.example.demo.services.RecipeService;

@Controller
public class RecipeController {
	
	private final static Logger log = LoggerFactory.getLogger(RecipeController.class);

	private static final String RECIPE_FORM_TEMPLATE = "recipe/form";
	private static final String RECIPE_SHOW_TEMPLATE = "recipe/show";
	
    private final RecipeService recipeService;
	private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
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
    	
    	RecipeCommand commandRecipe = recipeService.getRecipeCommandWithAllCategories();
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
    	Recipe recipe = recipeService.saveCommandRecipe(recipeCommand);
    	model.addAttribute("recipe", recipe);
    	model.addAttribute("comment", new Comment());
    	return RECIPE_SHOW_TEMPLATE;
    }
    
    /**
     * request to delete a recipe
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value ="/recipe/{id}/delete", method = RequestMethod.DELETE)
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
    	RecipeCommand result = this.recipeService.addEmptyIngredient(recipe);
    	
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
    @RequestMapping(value="/recipe", params={"deleteIngredient"}, method = RequestMethod.DELETE)
    public String deleteIngredientById( @RequestParam("deleteIngredient") long index,
    	RecipeCommand recipe, Model model ){
    	
    	RecipeCommand commandRecipe = this.recipeService.removeIngredient(recipe, index);
   
    	model.addAttribute("recipe", commandRecipe);
        return RECIPE_FORM_TEMPLATE;
    }
    
    /**
     * request to save a new comment
     * @param recipe
     * @param comment
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping(value="/recipe/{id}/comment")
    public String addComment(@PathVariable String id, RecipeCommand recipeCommand , @Valid  CommentCommand comment, 
    		BindingResult bindingResult, Model model) {
    	
    	long recipeId = Long.parseLong(id);
    	
    	if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            
            model.addAttribute("recipe", recipeCommand);
        	model.addAttribute("comment", comment);
            return RECIPE_SHOW_TEMPLATE;
        }
    	
    	
    	RecipeCommand resultsaved = this.recipeService.saveRecipeComment(recipeId, comment);
    	model.addAttribute("recipe", resultsaved);
    	model.addAttribute("comment", new Comment());
    	return RECIPE_SHOW_TEMPLATE;
    }
    

    
    
}