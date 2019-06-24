package com.example.demo.controllers;



import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.CommentCommand;
import com.example.demo.form.RecipeCommand;
import com.example.demo.services.CommentService;
import com.example.demo.services.RecipeService;

@Controller
public class CommentController {
	
	private static final  Logger log = LoggerFactory.getLogger(CommentController.class);

	
	private static final String RECIPE_SHOW_TEMPLATE = "recipe/show";
	
    private final RecipeService recipeService;

	private final CommentService commentService;

    public CommentController(RecipeService recipeService, CommentService commentService) {
        this.recipeService = recipeService;
        this.commentService = commentService;
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
    	
    	this.commentService.saveComment(recipeId, comment);
    	RecipeCommand recipe = recipeService.findRecipeCommandById(recipeId);
    	model.addAttribute("recipe", recipe);
    	model.addAttribute("comment", new CommentCommand());
    	return RECIPE_SHOW_TEMPLATE;
    }
    
    
        
}