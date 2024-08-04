package com.example.demo.controller;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.model.UserAccountForm;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.UserAccountService;
import com.example.demo.util.MessageUtils;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/savings")
@SessionAttributes("userId")
public class UserManagementController {
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private MessageUtils messageUtils;

	@GetMapping("/register")
	public String showRegistrationForm(Model model, @ModelAttribute UserAccountForm userAccountForm) {
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@Validated @ModelAttribute UserAccountForm userAccountForm, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		return "redirect:/savings";
	}

	@GetMapping("/login")
	public String showLoginForm(Model model, @ModelAttribute UserAccountForm userAccountForm) {
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@Validated @ModelAttribute UserAccountForm userAccountForm, BindingResult bindingResult,
			Model model, HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "login";
		}
		
		try {
			Long userId = authenticationService.authenticateUser(userAccountForm);
			model.addAttribute("userId", userId);
			return "redirect:/savings/user";
		}catch(AuthenticationException e) {
			model.addAttribute("UserNotFound",messageUtils.get("user.not.found"));
			return "login";
		}
	}
}
