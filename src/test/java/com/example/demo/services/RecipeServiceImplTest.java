package com.example.demo.services;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
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

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.converter.CommentCommandToComment;
import com.example.demo.converter.CommentToCommentCommand;
import com.example.demo.converter.RecipeCommandToRecipe;
import com.example.demo.converter.RecipeToRecipeCommand;
import com.example.demo.entities.Difficulty;
import com.example.demo.entities.Ingredient;
import com.example.demo.entities.Recipe;
import com.example.demo.entities.UnitOfMeasure;
import com.example.demo.form.CategoryCommand;
import com.example.demo.form.RecipeCommand;
import com.example.demo.repositories.RecipeRepository;


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
	CommentService ingredientService;
	@Mock
	CommentToCommentCommand commentToCommentCommand;
	@Mock
	CommentCommandToComment commentCommandToComment;

	private static final Logger log = LoggerFactory.getLogger(RecipeServiceImplTest.class);


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		recipeService = new RecipeServiceImpl(
				recipeRepository, 
				categoryService, 
				recipeCommandToRecipe, 
				recipeToRecipeCommand);
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
	public void getRecipesTest() {

		Recipe recipe1 = new Recipe();
		
		HashSet<Recipe> receipesData = new HashSet<>();
		receipesData.add(recipe1);
		
		when(recipeRepository.findAll()).thenReturn(receipesData);
		
		RecipeCommand recipeCommand = new RecipeCommand();
	
		
		when(recipeToRecipeCommand.convert((Recipe) ArgumentMatchers.any(Recipe.class))).thenReturn(recipeCommand);

		Set<RecipeCommand> recipes = recipeService.getRecipes();

		assertEquals(1, recipes.size());
		verify(recipeRepository, times(1)).findAll();
		verify(recipeToRecipeCommand, times(1)).convert((Recipe) ArgumentMatchers.any(Recipe.class));
		verify(recipeRepository, never()).findById(anyLong());
	}

    @Test
    public void testDeleteById() throws Exception {

        //given
        Long idToDelete = Long.valueOf(2L);

        //when
        recipeService.deleteById(idToDelete);

        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
	@Test
	public void findRecipeCommandByIdTest() {

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
		String ngredient1Name= "ingredient1";
		String ngredient2Name= "ingredient2";
		ingredient1.setName(ngredient1Name);
		ingredient1.setUom(UnitOfMeasure.DECILITER);
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(-5L);
		BigDecimal amount2 = new BigDecimal(5);
		ingredient2.setAmount(amount2);
		ingredient2.setUom(UnitOfMeasure.CUP);
		ingredient2.setName(ngredient2Name);

		recipeMock.addIngredient(ingredient1);
		recipeMock.addIngredient(ingredient2);

		when(recipeRepository.save((Recipe) ArgumentMatchers.any(Recipe.class))).thenReturn(recipeMock);

		when(recipeCommandToRecipe.convert((RecipeCommand) ArgumentMatchers.any(RecipeCommand.class))).thenReturn(recipeMock);

		RecipeCommand savedRecipe = new RecipeCommand();
		when(recipeToRecipeCommand.convert((Recipe) ArgumentMatchers.any(Recipe.class))).thenReturn(savedRecipe );

		//test
		RecipeCommand recipeReturned = recipeService.saveCommandRecipe(new RecipeCommand());

		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

		verify(recipeRepository).save(argumentCaptor.capture());
		Recipe recipeCaptor = argumentCaptor.getValue();
		assertTrue("recipe must contains two ingredients, actually : "+recipeCaptor.getIngredients().size(), recipeCaptor.getIngredients().size() == 2);
		Ingredient ingredientSaved1 = null;
		Ingredient ingredientSaved2 = null;
		for (Ingredient ingredient : recipeCaptor.getIngredients()) {
			if (ingredient.getName().equals(ngredient1Name)) {
				ingredientSaved1 = ingredient;
			}else if (ingredient.getName().equals(ngredient2Name)) {
				ingredientSaved2 = ingredient;
			}
		}
		assertNotNull(ingredientSaved1);
		assertNotNull(ingredientSaved2);
		assertEquals("amount must be equals to : "+amount1, amount1, ingredientSaved1.getAmount());
		assertEquals("amount must be equals to : "+amount2, amount2, ingredientSaved2.getAmount());
		assertEquals("uom must be equals to : "+UnitOfMeasure.DECILITER, UnitOfMeasure.DECILITER, ingredientSaved1.getUom());
		assertEquals("uom must be equals to : "+UnitOfMeasure.CUP, UnitOfMeasure.CUP, ingredientSaved2.getUom());

		assertTrue("id must be equals to 1 ", 1l == ingredientSaved1.getId());
		assertNull("id must be null",  ingredientSaved2.getId());
		
		verify(recipeRepository, times(1)).save((Recipe) ArgumentMatchers.any(Recipe.class));
		verify(recipeCommandToRecipe, times(1)).convert((RecipeCommand) ArgumentMatchers.any(RecipeCommand.class));
		verify(recipeToRecipeCommand, times(1)).convert((Recipe) ArgumentMatchers.any(Recipe.class));
		 
	}

	@Test
	public void addEmptyIngredientToForm() {

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
		RecipeCommand recipeReturned = recipeService.addEmptyIngredientToForm( recipeCommand);

		//check
		assertNotNull("musnt be null", recipeReturned);
		assertEquals("content must equals "+contentStr, recipeReturned.getContent(), contentStr);
		assertEquals("Difficulty must equals "+difficulty.toString(), recipeReturned.getDifficulty(), difficulty);
		assertEquals("recipe name must equals "+recipeName, recipeReturned.getName(), recipeName);
		assertTrue("prepTime must equals "+prepTime, recipeReturned.getPrepTime() == prepTime);
		assertTrue("cooktime must equals "+cooktime, recipeReturned.getCookTime() == cooktime);
		assertTrue("serving must equals "+serving, recipeReturned.getServings() == serving);
		assertTrue("id must equals '"+id+"'but its equals to "+recipeReturned.getId(), recipeReturned.getId() == id);
		assertEquals("RecipeCommand must contains 1 ingredient", recipeReturned.getIngredients().size(), 1);
		assertTrue("the ingredient id must be negativ", recipeReturned.getIngredients().get(0).getId() < 0);

	}

	@Test
	public void getRecipeCommandWithAllCategories() {

		//mock categoryService.findAllCategoryCommand
		List<CategoryCommand> commandCategories = new ArrayList<>();
		CategoryCommand categoryCommand1 =new CategoryCommand();
		categoryCommand1.setDescription("French");
		categoryCommand1.setChecked(false);
		CategoryCommand categoryCommand2 = new CategoryCommand();
		categoryCommand2.setDescription("Italian");
		categoryCommand2.setChecked(false);
		commandCategories.add(categoryCommand1);
		commandCategories.add(categoryCommand2);

		when(categoryService.findAllCategoryCommand()).thenReturn(commandCategories);
		//test
		RecipeCommand recipeCommand = null;
		try {
			recipeCommand = recipeService.getEmptyRecipeCommandWithAllCategories();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		//asserts
		assertNotNull("musnt be null", recipeCommand);
		assertNull("content must be null", recipeCommand.getContent());
		assertNull("Difficulty must be null ", recipeCommand.getDifficulty());
		assertNull("recipe name must be null", recipeCommand.getName());
		assertNull("prepTime must be null", recipeCommand.getPrepTime() );
		assertNull("cooktime must be null ", recipeCommand.getCookTime() );
		assertNull("serving must be null", recipeCommand.getServings());
		assertNull("id must be null", recipeCommand.getId());
		assertEquals("RecipeCommand must contains 2 categories", 2, recipeCommand.getCategories().size());
		verify(categoryService, times(1)).findAllCategoryCommand();

	}


}