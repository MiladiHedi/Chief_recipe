package com.example.demo.formcommand;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryCommand {
    
	private Long id;
	
	@NotBlank
    @Size(min = 2, max = 20 )
    private String description;
	
	private boolean checked;
	

	public CategoryCommand() {
	}
    
    public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
