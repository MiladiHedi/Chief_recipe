package com.example.demo.formcommand;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo.model.Difficulty;

public class RecipeCommand {
    private long id;
    
    @NotNull
    @Size(min = 5, max = 100 )
    private String name;
    
    @NotNull
    @Min(1)
    @Max(300)
    private Integer prepTime;
    
    @NotNull
    @Min(1)
    @Max(300)
    private Integer cookTime;
    
    @NotNull
    @Min(1)
    @Max(10)
    private Integer servings;
    
    private List<CommentCommand> comments = new ArrayList<>();
    private List<IngredientCommand> ingredients = new ArrayList<>();
    private List<CategoryCommand> categories = new ArrayList<>();
    
    @NotNull
    @Size(min = 100, max = 5000 )
    private String content;
    
    private Difficulty difficulty;
    

	public long getId() {
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


	public Integer getPrepTime() {
		return prepTime;
	}


	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
	}


	public Integer getCookTime() {
		return cookTime;
	}


	public void setCookTime(Integer cookTime) {
		this.cookTime = cookTime;
	}


	public Integer getServings() {
		return servings;
	}


	public void setServings(Integer servings) {
		this.servings = servings;
	}


	public List<CommentCommand> getComments() {
		return comments;
	}


	public void setComments(List<CommentCommand> comments) {
		this.comments = comments;
	}
	public List<IngredientCommand> getIngredients() {
		return ingredients;
	}


	public void setIngredients(List<IngredientCommand> ingredients) {
		this.ingredients = ingredients;
	}


	public List<CategoryCommand> getCategories() {
		return categories;
	}


	public void setCategories(List<CategoryCommand>  categories) {
		this.categories = categories;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Difficulty getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}


	@Override
	public String toString() {
		return "BeanRecipe [id=" + id + ", name=" + name + ", prepTime=" + prepTime + ", cookTime=" + cookTime
				+ ", servings=" + servings + ", comments=" + comments + ", ingredients=" + ingredients + ", categories="
				+ categories + ", content=" + content + ", difficulty=" + difficulty + "]";
	}


}
