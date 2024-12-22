package com.example.demo.controller;

import java.math.BigDecimal;

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

import com.example.demo.form.SavingsFormWithValidation;
import com.example.demo.model.MySavingRule;
import com.example.demo.model.WishItem;
import com.example.demo.service.MySavingRuleService;
import com.example.demo.service.SavingsBoxService;
import com.example.demo.service.SavingsService;
import com.example.demo.service.UserAccountService;
import com.example.demo.service.WishItemService;

@Controller
@RequestMapping("/savings/user")
public class UserSavingsController {
	@Autowired
	private SavingsService savingsService;
	@Autowired
	private WishItemService wishItemService;
	@Autowired
	private MySavingRuleService mySavingRuleService;
	@Autowired
	private UserAccountService userService;
	@Autowired
	private SavingsBoxService savingsBoxService;

	private Long userId;
	private String username;

	@ModelAttribute
	public void setUser(@SessionAttribute("userId") Long userId, @SessionAttribute("username") String username) {
		this.userId = userId;
		this.username = username;
	}

	@GetMapping
	public String getAllSavings(Model model, @ModelAttribute SavingsFormWithValidation savingsFormWithValidation) {
		model.addAttribute("savingsList", savingsService.getSavingsByUserId(userId));
		model.addAttribute("wishList", wishItemService.getSavingPurposeByUserId(userId));
		model.addAttribute("myRuleList", mySavingRuleService.getMySavingRuleByUserId(userId));
		model.addAttribute("totalSavings", savingsBoxService.getSavingBoxByUserId(userId).getTotalAmount());
		model.addAttribute("username", username);
		return "home";
	}

	@PostMapping
	public String createSavings(@RequestParam(name = "myRuleId", required = true) Long myRuleId,
			Model model) {

		MySavingRule myRule = (myRuleId == null) ? null : mySavingRuleService.getMySavingRuleById(myRuleId);
		savingsBoxService.updateAmount(userId, myRule.getAmount());
		return "redirect:/savings/user";
	}

	//
		@PostMapping("/withdraw")
		public String withdrawFromSavingsBox(@RequestParam("wishItemId") Long wishItemId) {
			WishItem selectedItem = wishItemService.getWishItembyId(wishItemId);
			BigDecimal withdrawalAmount = selectedItem.getNeededAmount();
			savingsBoxService.withdraw(userId, withdrawalAmount);
			wishItemService.deleteWishItem(wishItemId);

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
