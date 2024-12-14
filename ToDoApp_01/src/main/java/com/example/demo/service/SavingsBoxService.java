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
	
	public void updateAmount(Long userId, BigDecimal amount) {
		SavingsBox updatedSavingsBox = getByUserId(userId);
		updatedSavingsBox.updateTotalAmount(amount);
		repository.save(updatedSavingsBox);
	}
	public SavingsBox getByUserId(Long userId) {
		return repository.findByUserId(userId);
	}
	
}
