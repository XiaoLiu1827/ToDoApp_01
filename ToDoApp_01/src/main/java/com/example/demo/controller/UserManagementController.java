package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.UserAccountForm;
import com.example.demo.model.SavingsBox;
import com.example.demo.model.UserAccount;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.SavingsBoxService;
import com.example.demo.service.UserAccountService;
import com.example.demo.util.MessageUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/savings")
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
	public String registerUser(@Validated @ModelAttribute UserAccountForm userAccountForm, BindingResult bindingResult,
			@RequestParam String username, @RequestParam String password, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		if (bindingResult.hasErrors()) {
			return "register";
		}

		UserAccount user = new UserAccount();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole("USER");

		SavingsBox savingsBox = new SavingsBox();
		user.setSavingsBox(savingsBox);

		user = userAccountService.saveUser(user);

		savingsBox.setUserId(user.getId());
		savingsBoxService.saveSavingsBox(savingsBox);
		
		// 登録後に認証を実行
		authenticationService.authenticateUser(username, password, request, response);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAuth = authentication.isAuthenticated();
		
		Object securityContext = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
	    
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
