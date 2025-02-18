package com.example.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.form.UserAccountForm;
import com.example.demo.model.SavingsBox;
import com.example.demo.model.UserAccount;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.SavingsBoxService;
import com.example.demo.service.UserAccountService;
import com.example.demo.util.MessageUtils;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/savings")
@SessionAttributes({ "userId", "username" })
@RequiredArgsConstructor
public class UserManagementController {
	private final UserAccountService userAccountService;
	private final AuthenticationService authenticationService;
	private final MessageUtils messageUtils;
	private final PasswordEncoder passwordEncoder;
	private final SavingsBoxService savingsBoxService;

	@GetMapping("/register")
	public String showRegistrationForm(Model model, @ModelAttribute UserAccountForm userAccountForm) {
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@RequestParam String username, @RequestParam String password) {
		UserAccount user = new UserAccount();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole("USER");

		SavingsBox savingsBox = new SavingsBox();
		user.setSavingsBox(savingsBox);

		user = userAccountService.saveUser(user);

		savingsBox.setUserId(user.getId());
		savingsBoxService.saveSavingsBox(savingsBox);
		return "redirect:/savings/user";
	}

	@GetMapping("/login")
	public String showLoginForm(Model model, @ModelAttribute UserAccountForm userAccountForm) {
		return "login";
	}

//	@PostMapping("/login")
//	public String loginUser(@Validated @ModelAttribute UserAccountForm userAccountForm, BindingResult bindingResult,
//			Model model, HttpSession session) {
//		if (bindingResult.hasErrors()) {
//			return "login";
//		}
//
//		try {
//			UserAccount loginUser = authenticationService.authenticateUser(userAccountForm);
//			model.addAttribute("userId", loginUser.getId());
//			model.addAttribute("username", loginUser.getUsername());
//
//			return "redirect:/savings/user";
//		} catch (AuthenticationException e) {
//			model.addAttribute("UserNotFound", messageUtils.get("user.not.found"));
//			return "login";
//		}
//	}
}
