package com.example.demo.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.example.demo.converter.CategoryCommandToCategory;
import com.example.demo.entities.Category;
import com.example.demo.form.CategoryCommand;

public class CategoryCommandToCategoryTest {

    public static final String DESCRIPTION = "DESCRIPTION";
    public static final Long ID_VALUE = 1L;

    

    CategoryCommandToCategory converter;
    

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }
    
    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
    	CategoryCommand categoryCommand = new CategoryCommand();
    	
    	categoryCommand.setId(ID_VALUE);
    	categoryCommand.setDescription(DESCRIPTION);

	
        //when
    	Category category = converter.convert(categoryCommand);
        

        //then
        assertNotNull(category);
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }


}