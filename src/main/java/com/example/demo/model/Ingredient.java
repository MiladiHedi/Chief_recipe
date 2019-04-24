package com.example.demo.model;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal amount;

    @Enumerated(value = EnumType.STRING)
    private UnitOfMeasure unitOfMeasure;

    @ManyToOne
    private Recipe recipe;

    public Ingredient() {
    	this.unitOfMeasure = UnitOfMeasure.SELECT;
    }
    
    public Ingredient(String name, BigDecimal amount, UnitOfMeasure uom) {
        this.name = name;
        this.amount = amount;
        this.unitOfMeasure = uom;
    }
    
    public Ingredient(String name, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.name = name;
        this.amount = amount;
        this.unitOfMeasure = uom;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public UnitOfMeasure getUom() {
        return this.unitOfMeasure;
    }

    public void setUom(UnitOfMeasure uom) {
    	this.unitOfMeasure = uom;
    }
}
