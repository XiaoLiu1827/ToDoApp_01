package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.SavingPurpose;
import com.example.demo.model.Savings;
import com.example.demo.model.SavingsFormWithValidation;
import com.example.demo.model.UserAccount;
import com.example.demo.service.SavingPuroposeService;
import com.example.demo.service.SavingsService;

@Controller
@RequestMapping("/api/savings/user")
public class UserSavingsController {
	@Autowired
	private SavingsService savingsService;
	@Autowired
	private SavingPuroposeService purposeService;

	@GetMapping
	public String getAllSavings(Model model, @ModelAttribute SavingsFormWithValidation savingsFormWithValidation) {
		List<Savings> savingList = savingsService.getAllSavings();
		model.addAttribute("savingsList", savingList);

		List<SavingPurpose> purposeList = purposeService.getAllSavingPurposes();
		model.addAttribute("purposeList", purposeList);
		System.out.println(purposeList);

	//	model.addAttribute("savingsFormWithValidation", savingsFormWithValidation);

		return "savings";
	}

	@PostMapping
	public String createSavings(@RequestParam("purpose") Long purposeId,
			@Validated @ModelAttribute SavingsFormWithValidation savingsFormWithValidation,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			List<String> errorList = new ArrayList<>();
			bindingResult.getAllErrors().forEach(error -> {
				errorList.add(error.toString());
			});
			
			List<Savings> savingList = savingsService.getAllSavings();
			model.addAttribute("savingsList", savingList);

			List<SavingPurpose> purposeList = purposeService.getAllSavingPurposes();
			model.addAttribute("purposeList", purposeList);
			
			//model.addAttribute("savingsFormWithValidation", savingsFormWithValidation);

			return "savings";
		}

		Savings savings = new Savings(savingsFormWithValidation.getName(), savingsFormWithValidation.getAmount(), new UserAccount());
		savingsService.saveSavings(savings);

		SavingPurpose purpose = purposeService.getSavingPurposeById(purposeId);
		purpose.updateCurrentAmount(savings.getAmount());

		purposeService.saveSavingPurpose(purpose);

		return "redirect:/api/savings";
	}

	@GetMapping("/delete/{id}")
	public String deleteSavings(@PathVariable Long id) {
		savingsService.deleteSavings(id);

		return "redirect:/api/savings";
	}
}
