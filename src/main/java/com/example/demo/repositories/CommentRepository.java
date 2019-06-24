package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Comment;


public interface CommentRepository extends CrudRepository<Comment, Long> {
	

}
