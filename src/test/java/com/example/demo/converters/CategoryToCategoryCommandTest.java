package com.example.demo.converters;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.example.demo.converter.CategoryToCategoryCommand;
import com.example.demo.entities.Category;
import com.example.demo.entities.Recipe;
import com.example.demo.form.CategoryCommand;


public class CategoryToCategoryCommandTest {

	public static final String DESCRIPTION = "DESCRIPTION";
	public static final Long ID_VALUE = 1L;


	CategoryToCategoryCommand converter;

	@Before
	public void setUp() throws Exception {
		converter = new CategoryToCategoryCommand();
	}

	@Test
	public void testNullConvert() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Category()));
	}

	@Test
	public void testConvert() throws Exception {
		//given
		Category category = new Category();
		category.setId(ID_VALUE);
		category.setDescription(DESCRIPTION);
		
		Set<Recipe> recipes = new HashSet<>();
		Recipe recipe1 = new Recipe();
		recipe1.setId(12L);
		recipes.add(recipe1);
		category.setRecipes(recipes);
		

		//when
		CategoryCommand categoryCommand = converter.convert(category);
		//then
		assertNotNull(categoryCommand);
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());
        //assertTrue(categoryCommand.isChecked());
	}
}