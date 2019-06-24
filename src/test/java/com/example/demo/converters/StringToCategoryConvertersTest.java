package com.example.demo.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.converter.CategoryToCategoryCommand;
import com.example.demo.converter.StringToCategoryConverters;
import com.example.demo.entities.Category;
import com.example.demo.form.CategoryCommand;
import com.example.demo.repositories.CategoryRepository;

public class StringToCategoryConvertersTest {

    public static final String DESCRIPTION = "DESCRIPTION";
    public static final Long ID_VALUE = 1L;

    

    StringToCategoryConverters converter;
        
    @Mock
    CategoryRepository categoryRepository;
    
    @Before
    public void setUp() throws Exception {
    	
    	MockitoAnnotations.initMocks(this);
    	
    	
    	List<Category> categories = new ArrayList<>();
    	Category category1 = new Category();
    	category1.setId(1L);
    	category1.setDescription("category1");
    	Category category2 = new Category();
    	category2.setId(2L);
    	category2.setDescription("category2");
		categories.add(category1);
		categories.add(category2);
		when(categoryRepository.findAll()).thenReturn(categories );
	 
        converter = new StringToCategoryConverters( categoryRepository, 
        		new CategoryToCategoryCommand());
    }

    @Test
    public void testNullOrEmptyId() throws Exception {
        assertNull(converter.convert(null));
        assertNull(converter.convert(""));
    }
    
    @Test
    public void testExistingCategory() throws Exception {
    	CategoryCommand categoryCommand = converter.convert("1");
        assertNotNull(categoryCommand);
        assertEquals("category1", categoryCommand.getDescription());
    }
    
    @Test
    public void testBadCategoryId() throws Exception {
        assertNull(converter.convert("40"));
    }



}