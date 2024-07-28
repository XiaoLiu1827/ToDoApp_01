package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.SavingPurpose;
import com.example.demo.model.Savings;
import com.example.demo.model.SavingsFormWithValidation;
import com.example.demo.model.UserAccount;
import com.example.demo.service.SavingPuroposeService;
import com.example.demo.service.SavingsService;
import com.example.demo.service.UserAccountService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/savings/user")
public class UserSavingsController {
	@Autowired
	private SavingsService savingsService;
	@Autowired
	private SavingPuroposeService purposeService;
	@Autowired
	private UserAccountService userService;

	@GetMapping
	public String getAllSavings(Model model, @ModelAttribute SavingsFormWithValidation savingsFormWithValidation) {
		model.addAttribute("savingsList", savingsService.getAllSavings());
		model.addAttribute("purposeList", purposeService.getAllSavingPurposes());

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
		long userId = Optional.ofNullable((long)session.getAttribute("userId"))
				.orElseThrow(() -> new ResourceNotFoundException("loginUser not found"));
		Optional<UserAccount> opt = userService.findById(Long.valueOf(userId));
		//ユーザが見つからない場合未ログイン状態に設定
		if(opt.isEmpty()) {
            session.invalidate();
			return "redirect:/api/savings";
		}
		Savings savings = new Savings(savingsFormWithValidation.getName(), savingsFormWithValidation.getAmount(),
				opt.get());
		savingsService.saveSavings(savings);

		//SavingPurpose.amountの更新
		SavingPurpose purpose = purposeService.updateCurrentAmount(purposeId, savingsFormWithValidation.getAmount());

		return "redirect:/api/savings";
	}

	@GetMapping("/delete/{id}")
	public String deleteSavings(@PathVariable Long id) {
		savingsService.deleteSavings(id);

		return "redirect:/api/savings";
	}
}
