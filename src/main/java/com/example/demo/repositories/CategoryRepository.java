package com.example.demo.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
       
    Set<Category>  findAllByRecipes_Id(long recipeId);
}
