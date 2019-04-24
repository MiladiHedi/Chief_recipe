package com.example.demo.dto;

import java.util.Optional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.formcommand.CategoryCommand;
import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepository;

@Component
public class StringToCategoryConverters implements Converter<String, CategoryCommand> {

	private final CategoryRepository categoryRepository;
	private final CategoryToCategoryCommand categoryToCategoryCommandConverter;

	public StringToCategoryConverters(CategoryRepository categoryRepository,
			CategoryToCategoryCommand categoryToCategoryCommandConverter) {
		this.categoryRepository = categoryRepository;
		this.categoryToCategoryCommandConverter = categoryToCategoryCommandConverter;
    }
	
	@Override
	public CategoryCommand convert(String source) {
		Long parsedId = Long.parseLong(source);
		Optional<Category> category = categoryRepository.findById(parsedId);
		if (null == category.get()) {
			return null;
		} else {
			return categoryToCategoryCommandConverter.convert(category.get());
		}
		
	}

}
