package com.example.demo.services;

import java.util.List;

import com.example.demo.formcommand.CategoryCommand;
import com.example.demo.model.Category;


public interface CategoryService {

 
	List<CategoryCommand> findAllCategoryCommand();

	List<Category> findCategoriesByRecipeId(long recipeid);
	
	List<CategoryCommand>  findCompleteCategoriesCommandByRecipeId(long recipeid);
	
	List<CategoryCommand>  fillCommandCategories(List<CategoryCommand>  Categories);
}
