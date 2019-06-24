package com.example.demo.services;

import com.example.demo.form.CommentCommand;

public interface CommentService {

	CommentCommand saveComment(long recipeId, CommentCommand comment);
}
