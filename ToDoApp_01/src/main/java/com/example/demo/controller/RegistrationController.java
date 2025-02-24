//package com.example.demo.controller;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.demo.model.SavingsBox;
//import com.example.demo.model.UserAccount;
//import com.example.demo.repository.SavingsBoxRepository;
//import com.example.demo.repository.UserAccountRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/savings")
//public class RegistrationController {
//	private final UserAccountRepository repository;
//	private final PasswordEncoder passwordEncoder;
//	private final SavingsBoxRepository savingsBoxRepository;
//
//	@GetMapping("/register")
//	public String showRegistrationForm() {
//		return "register";
//	}
//
//	@PostMapping("/register")
//	public String registerUser(@RequestParam String username, @RequestParam String password) {
//		UserAccount user = new UserAccount();
//		user.setUsername(username);
//		user.setPassword(passwordEncoder.encode(password));
//		user.setRole("USER");
//
//		SavingsBox savingsBox = new SavingsBox();
//		user.setSavingsBox(savingsBox);
//
//		user = repository.save(user);
//
//		savingsBox.setUserId(user.getId());
//		savingsBoxRepository.save(savingsBox);
//		return "redirect:/savings/user";
//	}
//}
