package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/savings")
public class GuestSavingsController {

	@GetMapping
	public String getAllSavings() {

		return "guestSavings";
	}

	

}
