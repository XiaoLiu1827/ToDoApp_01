package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.model.MySavingRuleForm;
import com.example.demo.service.MySavingRuleService;

@Controller
@RequestMapping("/savings/mySavingRule")
@SessionAttributes("userId")
public class MyRuleController {
	@Autowired
	private MySavingRuleService mySavingRuleService;
	
	private Long userId;
	@ModelAttribute
    public void setUserId(@SessionAttribute("userId") Long userId) {
        this.userId = userId;
    }
	
	@GetMapping
	public String showMySavingRules(Model model, MySavingRuleForm mySavingRuleForm) {
		model.addAttribute("myRuleList", mySavingRuleService.getMySavingRuleByUserId(userId));
		return "mySavingRule";
	}

}