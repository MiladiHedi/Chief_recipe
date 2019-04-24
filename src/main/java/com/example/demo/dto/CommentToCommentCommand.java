package com.example.demo.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.formcommand.CommentCommand;
import com.example.demo.model.Comment;

import lombok.Synchronized;


@Component
public class CommentToCommentCommand implements Converter<Comment, CommentCommand>{

	private final static Logger log = LoggerFactory.getLogger(CommentToCommentCommand.class);

	
    @Synchronized
    @Nullable
    @Override
    public CommentCommand convert(Comment source) {
        if (source == null) {
            return null;
        }

        final CommentCommand commentBean = new CommentCommand();
        commentBean.setId(source.getId());
        commentBean.setId(source.getId());
        commentBean.setComContent(source.getComContent());
        commentBean.setAuthor(source.getAuthor());
        String strDate = "";
        try {
        	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
            strDate = dateFormat.format(source.getDate()); 
		} catch (Exception e) {
			log.error(e.getMessage());
		}
       
        
		commentBean.setDate(strDate);
        return commentBean;
    }
}
