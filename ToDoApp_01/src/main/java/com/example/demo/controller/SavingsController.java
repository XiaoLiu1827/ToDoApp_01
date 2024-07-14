package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.SavingPurpose;
import com.example.demo.model.Savings;
import com.example.demo.service.SavingPuroposeService;
import com.example.demo.service.SavingsService;

@Controller
@RequestMapping("/api/savings")
public class SavingsController {
	@Autowired
	private SavingsService savingsService;
	@Autowired
	private SavingPuroposeService purposeService;
	
	@GetMapping
	public String getAllSavings(Model model) {
		List<Savings> savingList  = savingsService.getAllSavings();
		model.addAttribute("savingsList",savingList);
		
		List<SavingPurpose> purposeList = purposeService.getAllSavingPurposes();
		model.addAttribute("purposeList", purposeList);
		System.out.println(purposeList);
		
		model.addAttribute("newSavings", new Savings());
		
		return "savings";
	}
	
	@PostMapping
	public String createSavings(@RequestParam("purpose") Long purposeId, @ModelAttribute Savings savings) {
		savingsService.saveSavings(savings);
		
		SavingPurpose purpose = purposeService.getSavingPurposeById(purposeId);
		purpose.updateCurrentAmount(savings.getAmount());
		
		purposeService.saveSavingPurpose(purpose);
		
		return "redirect:/api/savings";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteSavings(@PathVariable Long id){
		savingsService.deleteSavings(id);
		
		return "redirect:/api/savings";
	}
}
