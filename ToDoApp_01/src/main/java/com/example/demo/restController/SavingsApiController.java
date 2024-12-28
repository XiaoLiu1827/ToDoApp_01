package com.example.demo.restController;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.model.MySavingRule;
import com.example.demo.service.MySavingRuleService;
import com.example.demo.service.SavingsBoxService;

@RestController
@RequestMapping("/savings/api/")
public class SavingsApiController {
	@Autowired
	private SavingsBoxService savingsBoxService;
	@Autowired
	private MySavingRuleService mySavingRuleService;

	@PostMapping("/deposit/{ruleId}")
	public ResponseEntity<BigDecimal> updateMySavingRule(
			@SessionAttribute("userId") Long userId,
			@PathVariable Long ruleId) {
		MySavingRule myRule = (ruleId == null) ? null : mySavingRuleService.getMySavingRuleById(ruleId);
		BigDecimal updatedTotalAmount = savingsBoxService.updateAmount(userId, myRule.getAmount()).getTotalAmount();
		return ResponseEntity.ok(updatedTotalAmount);
	}
}
