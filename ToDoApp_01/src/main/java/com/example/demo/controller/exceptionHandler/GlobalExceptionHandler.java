package com.example.demo.controller.exceptionHandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.SessionExpiredException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
		model.addAttribute("errorMessage", ex.getMessage());
		model.addAttribute("resourceId", ex.getMessage());
		
		return "error/notFound";
	}
	
	@ExceptionHandler(SessionExpiredException.class)
	public String handleSessionExpiredException(SessionExpiredException ex, Model model) {
		model.addAttribute("sessionExpired", ex.getMessage());
		
		return "redirect:/savings/login";
	}
}
