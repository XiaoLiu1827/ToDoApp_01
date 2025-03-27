package com.example.demo.restController;

import java.math.BigDecimal;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.MySavingRule;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.MySavingRuleService;
import com.example.demo.service.TransactionalSavingsService;

import lombok.RequiredArgsConstructor;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/savings/api/")
@RequiredArgsConstructor
public class SavingsApiController {

	private final MySavingRuleService mySavingRuleService;

	private final AuthenticationService authService;


	private final TransactionalSavingsService transactionalSavingsService;
	
  private Long userId;

	@ModelAttribute
	public void setUser() {
		this.userId = authService.getAuthenticatedUserId();
	}

	@PostMapping("/deposit/{ruleId}")
	public ResponseEntity<String> depositByRule(@PathVariable Long ruleId) {

		if(ruleId == null) {
	        return ResponseEntity.badRequest().body("ルールIDが必要です。");
		}
		
		MySavingRule myRule = mySavingRuleService.getMySavingRuleById(ruleId)
			    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "貯金ルールが見つかりません"));

		BigDecimal updatedTotalAmount = transactionalSavingsService.processSavings(userId, myRule.getAmount(), myRule);

		return ResponseEntity.ok(updatedTotalAmount.toString());
	}

	@PostMapping("/save")
	public ResponseEntity<BigDecimal> saveByManualInput(@RequestParam(defaultValue = "0") String amount) {

		BigDecimal amountValue = new BigDecimal(amount);

		BigDecimal updatedTotalAmount = transactionalSavingsService.processSavings(userId, amountValue, null);

		return ResponseEntity.ok(updatedTotalAmount);
	}
}
