package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Recipe;


public interface RecipeRepository extends CrudRepository<Recipe, Long> {
	
	List<Recipe> findAllByName(String name); 
}
