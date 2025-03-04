package com.example.demo.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.MySavingRule;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionalSavingsService {

	private final SavingsBoxService savingsBoxService;

	private final SavingsService savingsService;

	@Transactional
	public BigDecimal processSavings(Long userId, BigDecimal amount, MySavingRule rule) {
		BigDecimal savingAmount = Optional.ofNullable(rule)
				.map(MySavingRule::getAmount)
				.orElse(amount);

		//貯金額を取得し、貯金総額を更新する
		BigDecimal updatedTotalAmount = savingsBoxService.updateAmount(userId, savingAmount).getTotalAmount();

		//貯金記録を保存する
		if (rule != null) {
			savingsService.saveWithRule(userId, rule);
		} else {
			savingsService.saveManualInput(userId, amount);
		}

		return updatedTotalAmount;
	}
}
