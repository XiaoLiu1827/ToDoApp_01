package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.form.SavingsFormWithValidation;
import com.example.demo.model.MySavingRule;
import com.example.demo.service.MySavingRuleService;
import com.example.demo.service.SavingsService;
import com.example.demo.service.UserAccountService;
import com.example.demo.service.WishItemService;

@Controller
@RequestMapping("/savings/user")
@SessionAttributes("userId")
public class UserSavingsController {
	@Autowired
	private SavingsService savingsService;
	@Autowired
	private WishItemService wishItemService;
	@Autowired
	private MySavingRuleService mySavingRuleService;
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
		model.addAttribute("wishList", wishItemService.getSavingPurposeByUserId(userId));
		model.addAttribute("myRuleList", mySavingRuleService.getMySavingRuleByUserId(userId));

		return "home";
	}

	@PostMapping
	public String createSavings(@RequestParam("wishItemId") Long wishItemId,
			@RequestParam(name = "myRuleId", required = true) Long myRuleId,
			Model model) {
		
		MySavingRule myRule = (myRuleId == null) ? null : mySavingRuleService.getMySavingRuleById(myRuleId);

//		//WishItem.CurrentAmountの更新
//		WishItem wishItem = wishItemService.updateCurrentAmount(wishItemId, myRule.getAmount());
//
//		//目標額到達可否のチェック
//		if (wishItem.checkProgress()) {
//			wishItemService.deleteWishItem(wishItemId);
//		}
		return "redirect:/savings/user";
	}

	//目標額到達時
	@PostMapping("/goalAchieved")
	public String goalAchieved(@RequestParam("wishItemId") Long wishItemId) {
		wishItemService.deleteWishItem(wishItemId);

		return "redirect:/savings/user";
	}

	//削除
	@GetMapping("/delete/{id}")
	public String deleteSavings(@PathVariable Long id) {
		savingsService.deleteSavings(id);

		return "redirect:/savings/user";
	}
}
