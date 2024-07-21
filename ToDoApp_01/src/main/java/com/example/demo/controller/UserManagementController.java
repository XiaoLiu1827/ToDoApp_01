package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.UserAccountForm;
import com.example.demo.service.SavingPuroposeService;
import com.example.demo.service.SavingsService;

@Controller
@RequestMapping("/api/savings")
public class UserManagementController {
	@Autowired
	private SavingsService savingsService;
	@Autowired
	private SavingPuroposeService purposeService;
	
	@GetMapping("/register")
    public String showRegistrationForm(Model model, @ModelAttribute UserAccountForm userAccountForm) {
        return "register";
    }
	
	@PostMapping("/register")
	public String registerUser(@Validated @ModelAttribute UserAccountForm userAccountForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        

        
        
        return "redirect:/api/savings";
    }
}
