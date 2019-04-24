package com.example.demo.model;


import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Recipe recipe;
    
    @Lob
    @NotBlank
    @Size(min = 3, max = 5000 )
    private String comContent;
    
    @NotBlank
    @Size(min = 3, max = 20 )
    private String author;
    
    private Date date; 
    
    
    public Comment(Recipe recipe, String content, String author, Date date) {
		super();
		this.recipe = recipe;
		this.comContent = content;
		this.author = author;
		this.date = date;
	}

	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

	public String getComContent() {
		return comContent;
	}

	public void setComContent(String comment) {
		this.comContent = comment;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

   
}
