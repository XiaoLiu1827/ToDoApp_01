package com.example.demo.controller.exceptionHandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
		model.addAttribute("erroMessage", ex.getMessage());
		model.addAttribute("resourceId", ex.getMessage());
		
		return "error/notFound";
	}
}
