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

import com.example.demo.form.SavingPurposeForm;
import com.example.demo.service.UserAccountService;

@Controller
@RequestMapping("/savings/user/addPurpose")
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
    public String showAddPurposeForm(Model model, SavingPurposeForm savingPurposeForm) {
    	return "addSavingPurpose";
    }
    
    @PostMapping
    public String addPurpose(@Validated @ModelAttribute SavingPurposeForm savingPurposeForm, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "addSavingPurpose";
		}		
		userAccountService.addSavingPurpose(userId, savingPurposeForm.getName(), savingPurposeForm.getNeededAmount());
		
		return "redirect:/savings/user";
    }
}
