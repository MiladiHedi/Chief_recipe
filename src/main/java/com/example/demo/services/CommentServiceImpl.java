package com.example.demo.services;


import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.converter.CommentCommandToComment;
import com.example.demo.converter.CommentToCommentCommand;
import com.example.demo.entities.Comment;
import com.example.demo.entities.Recipe;
import com.example.demo.exception.NotFoundException;
import com.example.demo.form.CommentCommand;
import com.example.demo.repositories.RecipeRepository;


@Service
public class CommentServiceImpl implements CommentService {

	private final RecipeRepository recipeRepository;
	private final CommentCommandToComment commentCommandToComment;
	private final CommentToCommentCommand commentToCommentCommand;

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

	public CommentServiceImpl(RecipeRepository recipeRepository,
			CommentCommandToComment commentCommandToComment,
			CommentToCommentCommand commentToCommentCommand) {

		this.recipeRepository = recipeRepository;
		this.commentCommandToComment = commentCommandToComment;
		this.commentToCommentCommand = commentToCommentCommand;
	}

	@Transactional
	@Override
	public CommentCommand saveComment(long recipeId, CommentCommand commentCommand) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if(!recipeOptional.isPresent()){
			throw new NotFoundException("Recipe not found for id: " + commentCommand.getRecipeId());
		} else {
			Recipe recipe = recipeOptional.get();
			// comment is a new object, no update is possible
			Comment comment = this.commentCommandToComment.convert(commentCommand);
			comment.setDate(new Date());
			comment.setId(null);
			recipe.addComment(comment);

			Recipe savedRecipe = recipeRepository.save(recipe);
		
			Optional<Comment> savedIngredientOptional = savedRecipe.getComments().stream()
					.filter(recipeComments -> recipeComments.getComContent().equals(comment.getComContent()))
					.filter(recipeComments -> recipeComments.getAuthor().equals(comment.getAuthor()))
					.findFirst();
			CommentCommand commentConverted = this.commentToCommentCommand.convert(savedIngredientOptional.get());
			return commentConverted;
		}
	}


}
