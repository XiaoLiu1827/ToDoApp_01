package com.example.demo.restController;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.MySavingRule;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.MySavingRuleService;
import com.example.demo.service.SavingsBoxService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/savings/api/")
@RequiredArgsConstructor
public class SavingsApiController {

	private final SavingsBoxService savingsBoxService;

	private final MySavingRuleService mySavingRuleService;

	private final AuthenticationService authService;

	private Long userId;

	@ModelAttribute
	public void setUser() {
		this.userId = authService.getAuthenticatedUserId();
	}

	@PostMapping("/deposit/{ruleId}")
	public ResponseEntity<BigDecimal> depositByRule(@PathVariable Long ruleId) {
		MySavingRule myRule = (ruleId == null) ? null : mySavingRuleService.getMySavingRuleById(ruleId);
		BigDecimal updatedTotalAmount = savingsBoxService.updateAmount(userId, myRule.getAmount()).getTotalAmount();
		return ResponseEntity.ok(updatedTotalAmount);
	}
	
	@PostMapping("/save")
	public ResponseEntity<BigDecimal> saveByManualInput(@RequestParam(defaultValue = "0") String amount){
	    BigDecimal amountValue = new BigDecimal(amount);
		BigDecimal updatedTotalAmount = savingsBoxService.updateAmount(userId, amountValue).getTotalAmount();
		return ResponseEntity.ok(updatedTotalAmount);
	}
}
