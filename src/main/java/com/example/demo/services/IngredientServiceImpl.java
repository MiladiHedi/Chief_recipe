package com.example.demo.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ingredient;
import com.example.demo.repositories.IngredientRepository;


@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

	
	@SuppressWarnings("unused")
	private final static Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
    	
    	this.ingredientRepository = ingredientRepository;
    }
    

	@Override
	public Ingredient findById(Long id) {
		Optional<Ingredient> ingredientOpt = ingredientRepository.findById(id);
        if (! ingredientOpt.isPresent()) {
			throw new RuntimeException("recipe not found");
		}
	return ingredientOpt.get();
	}


	@Override
	public void deleteById(Long id) {
		this.ingredientRepository.deleteById(id);
		
	}


	@Override
	public void save(Ingredient ingredient) {
		this.ingredientRepository.save(ingredient);
		
	}
}
