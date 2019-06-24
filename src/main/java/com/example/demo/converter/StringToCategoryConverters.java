package com.example.demo.converter;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Category;
import com.example.demo.form.CategoryCommand;
import com.example.demo.repositories.CategoryRepository;

@Component
public class StringToCategoryConverters implements Converter<String, CategoryCommand> {


	private final CategoryToCategoryCommand categoryToCategoryCommandConverter;
	private final CategoryRepository categoryRepository;

	private static final Logger log = LoggerFactory.getLogger(StringToCategoryConverters.class);


	public StringToCategoryConverters(CategoryRepository categoryRepository,
			CategoryToCategoryCommand categoryToCategoryCommandConverter) {
		this.categoryToCategoryCommandConverter = categoryToCategoryCommandConverter;
		this.categoryRepository = categoryRepository;
    }
	
	@Override
	public CategoryCommand convert(String source) {
		try {
			Long parsedId = Long.parseLong(source);
			Optional<Category> optCategory = findCategories(parsedId);
			if ( optCategory.isPresent()) {
				
				CategoryCommand categoryCommand = categoryToCategoryCommandConverter.convert(optCategory.get());
				if (null != categoryCommand) {
				}
				return categoryCommand;
			} else {
				return null;
			}
			
		} catch (Exception e) {
			log.error("Error, Unable to parse category id");
			log.error(e.toString());
			return null;
		}
		
	}

	private Optional<Category> findCategories(Long id) {
		Iterable<Category> categories = categoryRepository.findAll();
		for (Category category : categories) {

			if (category.getId().equals(id)) {
				return Optional.of(category);
			}
		}
		return Optional.empty();
	}

}
