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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.model.SavingPurpose;
import com.example.demo.model.Savings;
import com.example.demo.model.SavingsFormWithValidation;
import com.example.demo.service.SavingPuroposeService;
import com.example.demo.service.SavingsService;
import com.example.demo.service.UserAccountService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/savings/user")
@SessionAttributes("userId")
public class UserSavingsController {
	@Autowired
	private SavingsService savingsService;
	@Autowired
	private SavingPuroposeService purposeService;
	@Autowired
	private UserAccountService userService;
	
	private Long userId;

    @ModelAttribute
    public void setUserId(@SessionAttribute("userId") Long userId) {
        this.userId = userId;
    }

	@GetMapping
	public String getAllSavings(Model model, @ModelAttribute SavingsFormWithValidation savingsFormWithValidation) {
		model.addAttribute("savingsList", savingsService.getSavingsByUserId(userId));
		model.addAttribute("purposeList", purposeService.getSavingPurposeByUserId(userId));

		return "savings";
	}

	@PostMapping
	public String createSavings(@RequestParam("purpose") Long purposeId,
			@Validated @ModelAttribute SavingsFormWithValidation savingsFormWithValidation,
			BindingResult bindingResult, Model model, HttpSession session) {
		if (bindingResult.hasErrors()) {
			List<String> errorList = new ArrayList<>();
			//<テスト用>入力チェックエラー項目確認
			bindingResult.getAllErrors().forEach(error -> {
				errorList.add(error.toString());
			});
			
			model.addAttribute("savingsList", savingsService.getAllSavings());
			model.addAttribute("purposeList", purposeService.getAllSavingPurposes());

			return "savings";
		}
		
		Savings savings = new Savings(savingsFormWithValidation.getName(), savingsFormWithValidation.getAmount(),userId);
		savingsService.saveSavings(savings);

		//SavingPurpose.amountの更新
		SavingPurpose purpose = purposeService.updateCurrentAmount(purposeId, savingsFormWithValidation.getAmount());

		return "redirect:/savings/user";
	}

	@GetMapping("/delete/{id}")
	public String deleteSavings(@PathVariable Long id) {
		savingsService.deleteSavings(id);

		return "redirect:/savings/user";
	}
}
