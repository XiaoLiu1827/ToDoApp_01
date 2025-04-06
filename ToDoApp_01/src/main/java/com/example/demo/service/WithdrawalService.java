package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.WishItem;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WithdrawalService {
	private final WishItemService wishItemService;
	private final SavingsBoxService savingsBoxService;
	 
	@Transactional
	public BigDecimal updateTotalSavingsAndItemStatus(Long userId, Long wishItemId) {

		WishItem selectedItem = wishItemService.getWishItembyId(wishItemId);

		// 貯金額の更新処理
		BigDecimal updatedTotalAmount = updateSavingsAmount(userId, selectedItem);

		// アイテムの状態を更新
		updateItemStatus(updatedTotalAmount, selectedItem);

		return updatedTotalAmount;
	}

	private BigDecimal updateSavingsAmount(Long userId, WishItem selectedItem) {
		
		// 貯金額の更新を行う
		BigDecimal withdrawalAmount = selectedItem.getNeededAmount();
		return savingsBoxService.withdraw(userId, withdrawalAmount);
	}

	private void updateItemStatus(BigDecimal updatedTotalAmount, WishItem selectedItem) {
		
		// アイテムの状態を設定
		if (updatedTotalAmount.compareTo(BigDecimal.ZERO) < 0) {
			selectedItem.setStatus(2); // 前借中
		} else {
			selectedItem.setStatus(1); // 達成
		}

	}
}
