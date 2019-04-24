package com.example.demo.formcommand;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class IngredientCommand {
    private Long id;
    
    @Pattern(regexp="!^SELECT$")
    private String uom;
    
    
    @Size(min = 1, max = 100 )
    private BigDecimal amount;
    
    @NotNull
    @Size(min = 2, max = 200 )
    private String name;
    
	public IngredientCommand() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String unitOfMeasure) {
		this.uom = unitOfMeasure;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
