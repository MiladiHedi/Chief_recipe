package com.example.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.converter.CategoryToCategoryCommand;
import com.example.demo.entities.Category;
import com.example.demo.form.CategoryCommand;
import com.example.demo.repositories.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    
	private final CategoryToCategoryCommand categoryToCategoryBean;
	
	@SuppressWarnings("unused")
	private  static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public CategoryServiceImpl(CategoryRepository categoryRepository, 
    		CategoryToCategoryCommand categoryToCategoryBean) {
    	
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryBean = categoryToCategoryBean;
    }

    @Cacheable(value="AllCategory") 
	@Override
	public List<CategoryCommand>  findAllCategoryCommand() {
		
		List<Category> categories = new ArrayList<>();
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
	public List<CategoryCommand>  fillCommandCategories(List<CategoryCommand>  categories) {
		
		List<CategoryCommand>  allCategory = this.findAllCategoryCommand();
		return this.mergeCommandCategories(categories, allCategory);
	}

	@Override
	public List<Category> findCategoriesByRecipeId(long recipeid) {
		
		List<Category> categories = new ArrayList<>();
		categoryRepository.findAllByRecipes_Id(recipeid).iterator().forEachRemaining(categories::add);
        
		return categories;
	}

	@Override
	public List<CategoryCommand> mergeCommandCategories(List<CategoryCommand> categories,
			List<CategoryCommand> allCategory) {
		Map<String, CategoryCommand> recipeCategoryMap = new HashMap<String, CategoryCommand>();
		for (CategoryCommand categoryBean : allCategory) {
			recipeCategoryMap.put(categoryBean.getDescription(), categoryBean);		
		}
		//erase category of recipe (checked)
		for (CategoryCommand category : categories) {
			//each Categories are from client (ui), but incomplete, we set checked to true
			category.setChecked(true);
			recipeCategoryMap.put(category.getDescription(), category);		
		}
		
		List<CategoryCommand>  commandCategoryArray = new ArrayList<CategoryCommand>(recipeCategoryMap.values());
		return commandCategoryArray;
	}


}
