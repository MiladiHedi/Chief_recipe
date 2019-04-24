package com.example.demo.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.services.RecipeService;


@Controller
public class IndexController {

    private final RecipeService recipeService;
    private String appId = "unknown";

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
        
        // next code is only use to demonstrate load balencer
        Map<String, String> envs = System.getenv();
    	appId ="";
    	for (Map.Entry<String, String> entry : envs.entrySet()) {
    	    String key = entry.getKey();
    	    String value = entry.getValue();
    	    appId += "key :'"+key+"' value : '"+value+"' \n";
    	}
        appId = envs.get("APP_ID");
        
		
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
    	
        model.addAttribute("recipes", recipeService.getRecipes());
        model.addAttribute("appId", appId);

        return "index";
    }
}
