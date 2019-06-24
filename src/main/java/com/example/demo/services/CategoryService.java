package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.Category;
import com.example.demo.form.CategoryCommand;


public interface CategoryService {

 
	List<CategoryCommand> findAllCategoryCommand();

	List<Category> findCategoriesByRecipeId(long recipeid);
	
	List<CategoryCommand>  findCompleteCategoriesCommandByRecipeId(long recipeid);
	
	List<CategoryCommand>  fillCommandCategories(List<CategoryCommand>  Categories);

	List<CategoryCommand> mergeCommandCategories(List<CategoryCommand> categories, List<CategoryCommand> categories2);
}
