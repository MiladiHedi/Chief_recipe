package com.example.demo.services;

import com.example.demo.model.Ingredient;

public interface IngredientService {

    Ingredient findById(Long id);

	void deleteById(Long id);

	void save(Ingredient ingredient);
}
