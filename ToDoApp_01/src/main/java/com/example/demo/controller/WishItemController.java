package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.WishItemForm;

@Controller
@RequestMapping("/savings/wishItem")
public class WishItemController {

	@GetMapping
	public String showWishItemForm(Model model, WishItemForm wishItemForm) {
		return "wishItemForm";
	}
	
	

//	@PostMapping
//	public String createWishItem(@Validated @ModelAttribute WishItemForm wishItemForm,
//			BindingResult bindingResult, @RequestParam("image") MultipartFile imageFile,
//			Model model) {
//		if (bindingResult.hasErrors()) {
//			return "wishItemForm";
//		}
//		userAccountService.addWishItem(userId, wishItemForm.getName(), wishItemForm.getNeededAmount());
//
//		return "redirect:/savings/user";
//	}
}
