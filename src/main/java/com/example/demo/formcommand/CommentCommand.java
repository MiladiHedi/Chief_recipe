package com.example.demo.formcommand;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CommentCommand {
    private Long id;
    
	@NotBlank
    @Size(min = 3, max = 1000 )
    private String comContent;
	
	@NotBlank
    @Size(min = 3, max = 20 )
    private String author;
	
    private String date;
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComContent() {
		return comContent;
	}

	public void setComContent(String content) {
		this.comContent = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
    
}
