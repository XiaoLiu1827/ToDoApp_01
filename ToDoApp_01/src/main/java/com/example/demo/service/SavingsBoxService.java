package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.SavingsBox;
import com.example.demo.repository.SavingsBoxRepository;

@Service
public class SavingsBoxService {
	@Autowired
	SavingsBoxRepository repository;
	
	public SavingsBox updateAmount(Long userId, BigDecimal amount) {
		SavingsBox updatedSavingsBox = getSavingBoxByUserId(userId);
		updatedSavingsBox.updateTotalAmount(amount);
		return repository.save(updatedSavingsBox);
	}
	public SavingsBox getSavingBoxByUserId(Long userId) {
		return repository.findByUserId(userId);
	}
	
	//トランザクション管理でリポジトリ呼び出し不要？
	public BigDecimal withdraw(Long userId, BigDecimal amount) {
		SavingsBox updatedSavingsBox = getSavingBoxByUserId(userId);
		BigDecimal totalAmount = updatedSavingsBox.withDraw(amount);
		repository.save(updatedSavingsBox);
		return totalAmount;
	}
	
	public SavingsBox saveSavingsBox(SavingsBox savingsBox) {
		return repository.save(savingsBox);
	}
	
}
