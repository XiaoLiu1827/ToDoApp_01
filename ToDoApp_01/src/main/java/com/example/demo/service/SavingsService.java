package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Savings;
import com.example.demo.repository.SavingsRepository;

@Service
public class SavingsService {
	@Autowired
    private SavingsRepository savingsRepository;
	
	public List<Savings> getAllSavings(){
		return savingsRepository.findAll();
	}
	
	public Savings saveSavings(Savings savings) {
		return savingsRepository.save(savings);
	}
	
	public void deleteSavings(Long id) {
		savingsRepository.deleteById(id);
	}
}
