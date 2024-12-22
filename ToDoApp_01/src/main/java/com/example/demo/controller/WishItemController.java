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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.form.WishItemForm;
import com.example.demo.service.UserAccountService;

@Controller
@RequestMapping("/savings/wishItem")
@SessionAttributes("userId")
public class WishItemController {
	@Autowired
	private UserAccountService userAccountService;
	
	private Long userId;

    @ModelAttribute
    public void setUserId(@SessionAttribute("userId") Long userId) {
        this.userId = userId;
    }
    
    @GetMapping
    public String showWishItemForm(Model model, WishItemForm wishItemForm) {
    	return "wishItemForm";
    }
    
    @PostMapping
    public String addPurpose(@Validated @ModelAttribute WishItemForm wishItemForm, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "wishItemForm";
		}		
		userAccountService.addWishItem(userId, wishItemForm.getName(), wishItemForm.getNeededAmount());
		
		return "redirect:/savings/user";
    }
}
