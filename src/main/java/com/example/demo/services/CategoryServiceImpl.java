package com.example.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryToCategoryCommand;
import com.example.demo.formcommand.CategoryCommand;
import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    
	private final CategoryToCategoryCommand categoryToCategoryBean;

	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public CategoryServiceImpl(CategoryRepository categoryRepository, 
    		CategoryToCategoryCommand categoryToCategoryBean) {
    	
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryBean = categoryToCategoryBean;
    }

	@Override
	public List<CategoryCommand>  findAllCategoryCommand() {
		
		List<Category> categories = new ArrayList<Category>();
		categoryRepository.findAll().iterator().forEachRemaining(categories::add);
		List<CategoryCommand> commandCategories = convertCategories(categories);
		return commandCategories;
	}

	private List<CategoryCommand> convertCategories(List<Category> categories) {
		
		List<CategoryCommand> categoriesList =  new ArrayList<CategoryCommand>();
		for (Category category : categories) {
			categoriesList.add(this.categoryToCategoryBean.convert(category));
		}
 
		return categoriesList;
	}

	@Override
	public List<CategoryCommand> findCompleteCategoriesCommandByRecipeId(long recipeid) {
		List<Category> categoriesCommandList = findCategoriesByRecipeId(recipeid);
		List<CategoryCommand> fullCategories = fillCommandCategories( convertCategories(categoriesCommandList));
		return fullCategories;
	}
	
	
	/**
	 * fill add all categories from db to a CommandCategories array from client
	 */
	@Override
	public List<CategoryCommand>  fillCommandCategories(List<CategoryCommand>  Categories) {
		List<CategoryCommand>  allCategory = this.findAllCategoryCommand();
		
		Map<String, CategoryCommand> recipeCategoryMap = new HashMap<String, CategoryCommand>();
		for (CategoryCommand categoryBean : allCategory) {
			recipeCategoryMap.put(categoryBean.getDescription(), categoryBean);		
		}
		//erase category of recipe (checked)
		for (CategoryCommand category : Categories) {
			//each Categories are from client (ui), but incomplete, we set checked to true
			category.setChecked(true);
			recipeCategoryMap.put(category.getDescription(), category);		
		}
		
		List<CategoryCommand>  commandCategoryArray = new ArrayList<CategoryCommand>(recipeCategoryMap.values());
		return commandCategoryArray;
	}

	@Override
	public List<Category> findCategoriesByRecipeId(long recipeid) {
		
		List<Category> categories = new ArrayList<Category>();
		categoryRepository.findAllByRecipes_Id(recipeid).iterator().forEachRemaining(categories::add);
        
		return categories;
	}


}
