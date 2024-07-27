package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.UserAccount;
import com.example.demo.model.UserAccountForm;
import com.example.demo.service.UserAccountService;

@Controller
@RequestMapping("/savings")
public class UserManagementController {
	@Autowired
	private UserAccountService userAccountService;

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
			Model model) {
		if (bindingResult.hasErrors()) {
			return "login";
		}

		Optional<UserAccount> opt = userAccountService.findByUsernameAndPassword(userAccountForm.getUsername(),
				userAccountForm.getPassword());
		if (opt.isEmpty()) {
			final String  UserNotFount = "User not found.";
			model.addAttribute("UserNotFound", UserNotFount);
			return "login";
		}else {
			return "redirect:/savings/user";
		}
	}
}
