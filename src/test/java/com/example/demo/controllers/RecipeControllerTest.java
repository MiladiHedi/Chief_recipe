package com.example.demo.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.entities.Recipe;
import com.example.demo.services.CategoryService;
import com.example.demo.services.CommentService;
import com.example.demo.services.RecipeService;

//@PropertySource(value = "classpath:application-test.yml", encoding = "UTF-8")
public class RecipeControllerTest {


    @Mock
    RecipeService recipeService;
    @Mock
    CommentService commentService;
    @Mock
    CategoryService categoryService;
    
    RecipeController controller;

	private static final Logger log = LoggerFactory.getLogger(RecipeControllerTest.class);

	
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService, categoryService, commentService);
    }

    @Test
    public void testGetRecipe() throws Exception {

        Recipe recipe = new Recipe();
        
        recipe.setId(1L);
        log.debug("recipe id = '" + recipe.getId() + "'");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(recipeService.findById(recipe.getId())).thenReturn(recipe);
        
        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));
    }
}