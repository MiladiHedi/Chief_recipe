package com.example.demo.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Comment;
import com.example.demo.form.CommentCommand;

import lombok.Synchronized;


@Component
public class CommentToCommentCommand implements Converter<Comment, CommentCommand>{

	private static final Logger log = LoggerFactory.getLogger(CommentToCommentCommand.class);

	
    @Synchronized
    @Nullable
    @Override
    public CommentCommand convert(Comment source) {
        if (source == null) {
            return null;
        }

        CommentCommand commentCommand = new CommentCommand();
        commentCommand.setId(source.getId());
        commentCommand.setComContent(source.getComContent());
        commentCommand.setAuthor(source.getAuthor());
        if (null != source.getRecipe()) {
            commentCommand.setRecipeId(source.getRecipe().getId());

		}

        try {
        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);  
            String strDate = dateFormat.format(source.getDate()); 
            commentCommand.setDate(strDate);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		

        return commentCommand;
    }
}
