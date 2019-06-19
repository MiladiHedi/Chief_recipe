package com.example.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.exception.NotFoundException;

@ControllerAdvice
public class ErrorController {
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleBadRequestError(Exception exception){

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("error400");
		modelAndView.addObject("exception", exception);

		return modelAndView;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleInternalServerError(Exception exception){

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("error404");
		modelAndView.addObject("exception", exception);

		return modelAndView;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ModelAndView handleResourceNotFound(Exception exception){

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("error500");
		modelAndView.addObject("exception", exception);

		return modelAndView;
	}
	
	
}