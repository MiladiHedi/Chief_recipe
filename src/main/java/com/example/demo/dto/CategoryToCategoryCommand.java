package com.example.demo.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.formcommand.CategoryCommand;
import com.example.demo.model.Category;

import lombok.Synchronized;


@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }

        final CategoryCommand categoryBean = new CategoryCommand();

        categoryBean.setId(source.getId());
        categoryBean.setDescription(source.getDescription());
        //categoryBean.setChecked(true);

        return categoryBean;
    }
}
