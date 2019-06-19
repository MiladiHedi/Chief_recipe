package com.example.demo.services;


import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.in;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

import com.example.demo.dto.CommentCommandToComment;
import com.example.demo.dto.CommentToCommentCommand;
import com.example.demo.dto.RecipeCommandToRecipe;
import com.example.demo.dto.RecipeToRecipeCommand;
import com.example.demo.formcommand.CategoryCommand;
import com.example.demo.formcommand.CommentCommand;
import com.example.demo.formcommand.RecipeCommand;
import com.example.demo.model.Difficulty;
import com.example.demo.model.Ingredient;
import com.example.demo.model.Recipe;
import com.example.demo.model.UnitOfMeasure;
import com.example.demo.repositories.RecipeRepository;

//@PropertySource(value = "classpath:application-test.yml", encoding = "UTF-8")
public class RecipeServiceImplTest {

	RecipeServiceImpl recipeService;

	@Mock
	RecipeRepository recipeRepository;
	@Mock
	CategoryService categoryService;
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	@Mock
	IngredientService ingredientService;
	@Mock
	CommentToCommentCommand commentToCommentCommand;
	@Mock
	CommentCommandToComment commentCommandToComment;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		recipeService = new RecipeServiceImpl(
				recipeRepository, 
				categoryService, 
				recipeCommandToRecipe, 
				recipeToRecipeCommand, 
				ingredientService, 
				commentCommandToComment, 
				commentToCommentCommand);

	}

	@Test
	public void getRecipeByIdTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		Recipe recipeReturned = recipeService.findById(1L);

		assertNotNull("Null recipe returned", recipeReturned);
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, never()).findAll();
	}

	@Test
	public void getRecipesTest() throws Exception {

		Recipe recipe = new Recipe();
		HashSet<Recipe> receipesData = new HashSet<Recipe>();
		receipesData.add(recipe);

		when(recipeService.getRecipes()).thenReturn(receipesData);

		Set<Recipe> recipes = recipeService.getRecipes();

		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1)).findAll();
		verify(recipeRepository, never()).findById(anyLong());
	}

	@Test
	public void findRecipeCommandByIdTest() throws Exception {

		//mocking
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		List<CategoryCommand> commandCategories = new ArrayList<>();

		CategoryCommand categoryCommand1 =new CategoryCommand();
		categoryCommand1.setDescription("French");
		categoryCommand1.setChecked(false);
		CategoryCommand categoryCommand2 = new CategoryCommand();
		categoryCommand2.setDescription("Italian");
		categoryCommand2.setChecked(false);

		commandCategories.add(categoryCommand1);
		commandCategories.add(categoryCommand2);
		Collection<CategoryCommand> anyCategoryCommandCol = ArgumentMatchers.anyCollection();
		when(categoryService.fillCommandCategories((List<CategoryCommand>) anyCategoryCommandCol)).thenReturn(commandCategories);

		RecipeCommand recipeCommandmock = new RecipeCommand();
		String contentStr = "content";
		int cooktime = 5;
		Difficulty difficulty = Difficulty.HARD;
		long id = 50;
		String recipeName = "recipeName";
		int prepTime = 10;
		int serving = 3;
		recipeCommandmock.setContent(contentStr);
		recipeCommandmock.setCookTime(cooktime);
		recipeCommandmock.setDifficulty(difficulty);
		recipeCommandmock.setId(id);
		recipeCommandmock.setName(recipeName);
		recipeCommandmock.setPrepTime(prepTime);
		recipeCommandmock.setServings(serving);
		when(recipeToRecipeCommand.convert((Recipe) ArgumentMatchers.any(Recipe.class))).thenReturn(recipeCommandmock);

		//test
		RecipeCommand RecipeCommandReturned = recipeService.findRecipeCommandById(1L);

		assertNotNull("Null recipe returned", RecipeCommandReturned);
		assertEquals("RecipeCommand must contains 2 categories", RecipeCommandReturned.getCategories().size(), 2);
		assertEquals("content must equals "+contentStr, RecipeCommandReturned.getContent(), contentStr);
		assertEquals("Difficulty must equals "+difficulty.toString(), RecipeCommandReturned.getDifficulty(), difficulty);
		assertEquals("recipe name must equals "+recipeName, RecipeCommandReturned.getName(), recipeName);
		assertTrue("prepTime must equals "+prepTime, RecipeCommandReturned.getPrepTime() == prepTime);
		assertTrue("cooktime must equals "+cooktime, RecipeCommandReturned.getCookTime() == cooktime);
		assertTrue("serving must equals "+serving, RecipeCommandReturned.getServings() == serving);
		assertTrue("id must equals "+id, RecipeCommandReturned.getId() == id);

		verify(recipeToRecipeCommand, times(1)).convert((Recipe) ArgumentMatchers.any(Recipe.class));
		verify(categoryService, times(1)).fillCommandCategories((List<CategoryCommand>) anyCategoryCommandCol);


	}

	//RecipeService recipeServiceMock = Mockito.spy(recipeService);

	//Mockito.doReturn(true).when(recipeServiceMock).se.runInGround("ground");
	@Test
	public void saveCommandRecipeTest() {

		//mock recipeRepository.save(Recipe)
		
		Recipe recipeMock = new Recipe();
		String contentStr = "content";
		int cooktime = 5;
		Difficulty difficulty = Difficulty.HARD;
		long id = 50;
		String recipeName = "recipeName";
		int prepTime = 10;
		int serving = 3;
			
		recipeMock.setContent(contentStr);
		recipeMock.setCookTime(cooktime);
		recipeMock.setDifficulty(difficulty);
		recipeMock.setId(id);
		recipeMock.setName(recipeName);
		recipeMock.setPrepTime(prepTime);
		recipeMock.setServings(serving);
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		BigDecimal amount1 = new BigDecimal(1);
		ingredient1.setAmount(amount1);
		ingredient1.setName("ingredient1");
		ingredient1.setUom(UnitOfMeasure.DECILITER);
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(-5L);
		BigDecimal amount2 = new BigDecimal(5);
		ingredient2.setAmount(amount2);
		ingredient2.setUom(UnitOfMeasure.CUP);
		ingredient2.setName("ingredient2");
		assertTrue( ingredient2.getId() < 0);

		recipeMock.addIngredient(ingredient1);
		recipeMock.addIngredient(ingredient2);
		
		when(recipeRepository.save((Recipe) ArgumentMatchers.any(Recipe.class))).thenReturn(recipeMock);
		
		when(recipeCommandToRecipe.convert((RecipeCommand) ArgumentMatchers.any(RecipeCommand.class))).thenReturn(recipeMock);
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		//test
		Recipe recipeReturned = recipeService.saveCommandRecipe(new RecipeCommand());

		assertNotNull("Null recipe returned", recipeReturned);
		assertEquals("RecipeCommand must contains 0 categories", 0, recipeReturned.getCategories().size());
		assertEquals("content must equals "+contentStr, recipeReturned.getContent(), contentStr);
		assertEquals("Difficulty must equals "+difficulty.toString(), recipeReturned.getDifficulty(), difficulty);
		assertEquals("recipe name must equals "+recipeName, recipeReturned.getName(), recipeName);
		assertTrue("prepTime must equals "+prepTime, recipeReturned.getPrepTime() == prepTime);
		assertTrue("cooktime must equals "+cooktime, recipeReturned.getCookTime() == cooktime);
		assertTrue("serving must equals "+serving, recipeReturned.getServings() == serving);
		assertTrue("id must equals "+id, recipeReturned.getId() == id);

		//verify(recipeToRecipeCommand, times(1)).convert((Recipe) ArgumentMatchers.any(Recipe.class));
		//verify(recipeRepository, times(1)).save((Recipe) ArgumentMatchers.any(Recipe.class));
		
		
		verify(recipeRepository).save(argumentCaptor.capture());
		Recipe recipeCaptor = argumentCaptor.getValue();
		assertTrue("recipe must contains two ingredients, actually : "+recipeCaptor.getIngredients().size(), recipeCaptor.getIngredients().size() == 2);
		Ingredient[] ingredientArray = recipeCaptor.getIngredients().toArray(new Ingredient[2]);
		
		assertEquals("ingredient name must be ingredient1", "ingredient1", ingredientArray[0].getName());
		assertEquals("ingredient name must be ingredient2","ingredient2", ingredientArray[1].getName());
		assertEquals("amount must be equals to : "+amount1, amount1, ingredientArray[0].getAmount());
		assertEquals("amount must be equals to : "+amount2, amount2, ingredientArray[1].getAmount());
		assertEquals("uom must be equals to : "+UnitOfMeasure.DECILITER, UnitOfMeasure.DECILITER, ingredientArray[0].getUom());
		assertEquals("uom must be equals to : "+UnitOfMeasure.CUP, UnitOfMeasure.CUP, ingredientArray[1].getUom());
		
		assertTrue("id must be equals to 1 ", 1l == ingredientArray[0].getId());
		assertNull("id must be null",  ingredientArray[1].getId());
		//assertTrue(recipeCaptor.getIngredients().size() == 2);

	}

	@Test
	public void addEmptyIngredient() {
		//RecipeCommand addEmptyIngredient(RecipeCommand recipeCommand);
		//mock
		List<CategoryCommand> commandCategories = new ArrayList<>();

		CategoryCommand categoryCommand1 =new CategoryCommand();
		categoryCommand1.setDescription("French");
		categoryCommand1.setChecked(false);
		CategoryCommand categoryCommand2 = new CategoryCommand();
		categoryCommand2.setDescription("Italian");
		categoryCommand2.setChecked(false);

		commandCategories.add(categoryCommand1);
		commandCategories.add(categoryCommand2);
		Collection<CategoryCommand> anyCategoryCommandCol = ArgumentMatchers.anyCollection();
		when(categoryService.fillCommandCategories((List<CategoryCommand>) anyCategoryCommandCol)).thenReturn(commandCategories);

		RecipeCommand recipeCommand = new RecipeCommand();
		String contentStr = "content";
		int cooktime = 5;
		Difficulty difficulty = Difficulty.HARD;
		long id = 50;
		String recipeName = "recipeName";
		int prepTime = 10;
		int serving = 3;
		recipeCommand.setContent(contentStr);
		recipeCommand.setCookTime(cooktime);
		recipeCommand.setDifficulty(difficulty);
		recipeCommand.setId(id);
		recipeCommand.setName(recipeName);
		recipeCommand.setPrepTime(prepTime);
		recipeCommand.setServings(serving);
		
		//test
		RecipeCommand recipeReturned = recipeService.addEmptyIngredient( recipeCommand);
		
		//check
		assertNotNull("Null recipe returned", recipeReturned);
		assertEquals("RecipeCommand must contains 2 categories", recipeReturned.getCategories().size(), 2);
		assertEquals("content must equals "+contentStr, recipeReturned.getContent(), contentStr);
		assertEquals("Difficulty must equals "+difficulty.toString(), recipeReturned.getDifficulty(), difficulty);
		assertEquals("recipe name must equals "+recipeName, recipeReturned.getName(), recipeName);
		assertTrue("prepTime must equals "+prepTime, recipeReturned.getPrepTime() == prepTime);
		assertTrue("cooktime must equals "+cooktime, recipeReturned.getCookTime() == cooktime);
		assertTrue("serving must equals "+serving, recipeReturned.getServings() == serving);
		assertTrue("id must equals '"+id+"'but its equals to "+recipeReturned.getId(), recipeReturned.getId() == id);
		assertEquals("RecipeCommand must contains 1 ingredient", recipeReturned.getIngredients().size(), 1);
		assertTrue("the ingredient id must be negativ", recipeReturned.getIngredients().get(0).getId() < 0);
		
		verify(categoryService, times(1)).fillCommandCategories((List<CategoryCommand>) anyCategoryCommandCol);

	}
	
	@Test
	public void removeIngredient() {
		//RecipeCommand removeIngredient(RecipeCommand recipeCommand, long ingredientId);
	}

	@Test
	public void saveRecipeComment() {
		//	RecipeCommand saveRecipeComment(long recipeId, CommentCommand commentCommand);


	}
	
	@Test
	public void getRecipeCommandWithAllCategories() {
		//RecipeCommand getRecipeCommandWithAllCategories();

	}


}