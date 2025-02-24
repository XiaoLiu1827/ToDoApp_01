package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.MySavingRuleForm;
import com.example.demo.model.MySavingRule;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.MySavingRuleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/savings/mySavingRule")
@RequiredArgsConstructor
public class SavingRuleController {
	
	private final MySavingRuleService mySavingRuleService;
	
	private final AuthenticationService authService;

	private Long userId;

	@ModelAttribute
	public void setUser() {
		this.userId = authService.getAuthenticatedUserId();
	}
	
	@GetMapping
	public String showMySavingRules(Model model, MySavingRuleForm mySavingRuleForm) {
		model.addAttribute("myRuleList", mySavingRuleService.getMySavingRuleByUserId(userId));
		return "addSavingsRule";
	}

	@PostMapping
	public String saveMySavingRules(Model model, MySavingRuleForm mySavingRuleForm) {		
		MySavingRule myRule = MySavingRule.builder()
		        .title(mySavingRuleForm.getTitle())
		        .userId(userId)
		        .amount(mySavingRuleForm.getAmount())
		        .frequency(mySavingRuleForm.getFrequency())
		        .build();
		mySavingRuleService.saveMySavingRule(myRule);
		return "redirect:/savings/user";
	}
	
//	@PostMapping("/update/{id}")
//	public String updatetMySavingRule(@PathVariable Long id, @RequestBody MySavingRule updatedRule) {
//		MySavingRule result = mySavingRuleService.updateMySavingRule(id, updatedRule);
//		
//		return "redirect:/savings/user";
//	}
}