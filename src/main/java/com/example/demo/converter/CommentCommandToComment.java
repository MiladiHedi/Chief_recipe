package com.example.demo.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Comment;
import com.example.demo.entities.Recipe;
import com.example.demo.form.CommentCommand;

import lombok.Synchronized;


@Component
public class CommentCommandToComment implements Converter<CommentCommand, Comment> {

	private static final  Logger log = LoggerFactory.getLogger(CommentCommandToComment.class);

    @Synchronized
    @Nullable
    @Override
    public Comment convert(CommentCommand source) {
        if(source == null) {
            return null;
        }
        
        Comment comment = new Comment();
        comment.setId(source.getId());
        comment.setComContent(source.getComContent());
        comment.setAuthor(source.getAuthor());
        
        Date date = null;
        if (null != source.getDate()) {
        	try {
    			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(source.getDate());
    			comment.setDate(date);
    		} catch (ParseException e) {
    			log.error(e.getMessage());
    		}
		}
        
        if(source.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            recipe.addComment(comment);
        }
        
        return comment;
    }
}
