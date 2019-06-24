package com.example.demo.services;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.converter.CommentCommandToComment;
import com.example.demo.converter.CommentToCommentCommand;
import com.example.demo.converter.RecipeCommandToRecipe;
import com.example.demo.converter.RecipeToRecipeCommand;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Difficulty;
import com.example.demo.entities.Recipe;
import com.example.demo.form.CommentCommand;
import com.example.demo.repositories.RecipeRepository;


public class CommentServiceImplTest {

	CommentServiceImpl commentService;

	@Mock
	RecipeRepository recipeRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		commentService = new CommentServiceImpl(
				recipeRepository, 
				new CommentCommandToComment(), 
				new CommentToCommentCommand());
	}

	@Test
	public void saveRecipeComment() {
		
		
		//mock this.findById(recipeId);
		
		Recipe recipeMockForFindById = new Recipe();
		String contentStr = "content";
		int cooktime = 5;
		Difficulty difficulty = Difficulty.HARD;
		Long recipeId = 50L;
		String recipeName = "recipeName";
		int prepTime = 10;
		int serving = 3;

		recipeMockForFindById.setContent(contentStr);
		recipeMockForFindById.setCookTime(cooktime);
		recipeMockForFindById.setDifficulty(difficulty);
		recipeMockForFindById.setId(recipeId);
		recipeMockForFindById.setName(recipeName);
		recipeMockForFindById.setPrepTime(prepTime);
		recipeMockForFindById.setServings(serving);
		Optional<Recipe> recipeMockOptForFindById = Optional.of(recipeMockForFindById);
		when(recipeRepository.findById(anyLong())).thenReturn(recipeMockOptForFindById);
		
		
		
		Recipe recipeMock = new Recipe();
	

		recipeMock.setContent(contentStr);
		recipeMock.setCookTime(cooktime);
		recipeMock.setDifficulty(difficulty);
		recipeMock.setId(recipeId);
		recipeMock.setName(recipeName);
		recipeMock.setPrepTime(prepTime);
		recipeMock.setServings(serving);
		
		Comment commentMock = new Comment();
		String author="author";
		Long commentId =5L;
		String commentStr= "content";
		commentMock.setAuthor(author);
		commentMock.setComContent(commentStr);
		commentMock.setId(commentId);
		Date date = new Date();
		commentMock.setDate(date);
		recipeMock.addComment(commentMock);
		when(recipeRepository.save((Recipe) ArgumentMatchers.any(Recipe.class))).thenReturn(recipeMock);

		//test		
		CommentCommand commentCommand = new CommentCommand();
		commentCommand.setAuthor(author);
		commentCommand.setComContent(commentStr);
		
		CommentCommand commentCommandReturned = null;
		commentCommandReturned = commentService.saveComment(recipeMock.getId(), commentCommand);

		//asserts				
		assertNotNull("comment musnt be null", commentCommandReturned);
		assertEquals("comment recipeId musnt be equals to "+recipeId, recipeId, commentCommandReturned.getRecipeId());
		assertNotNull(" comment date musnt be null", commentCommandReturned.getDate());
		assertTrue("comment id  musnt be greater than 0", commentCommandReturned.getId() >0);
		assertEquals("Recipe id must equals to  "+recipeId, recipeId, commentCommandReturned.getRecipeId());
		assertEquals("comment id must equals to  "+commentId, commentId,  commentCommandReturned.getId());
		assertEquals("comment author must equals to  "+commentCommand.getAuthor(), commentCommand.getAuthor(),  commentCommandReturned.getAuthor());
		assertEquals( commentCommand.getComContent(),  commentCommandReturned.getComContent());

		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save((Recipe) ArgumentMatchers.any(Recipe.class));


	}
}