package com.example.demo.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.example.demo.formcommand.CommentCommand;
import com.example.demo.model.Comment;

import lombok.Synchronized;


@Component
public class CommentCommandToComment implements Converter<CommentCommand, Comment> {

	private final static Logger log = LoggerFactory.getLogger(CommentCommandToComment.class);

    @Synchronized
    @Nullable
    @Override
    public Comment convert(CommentCommand source) {
        if(source == null) {
            return null;
        }

        final Comment comment = new Comment();
        comment.setId(source.getId());
        comment.setComContent(source.getComContent());
        comment.setAuthor(source.getAuthor());
        Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(source.getDate());
		} catch (ParseException e) {
			log.error(e.getMessage());
		}  
        comment.setDate(date);
        return comment;
    }
}
