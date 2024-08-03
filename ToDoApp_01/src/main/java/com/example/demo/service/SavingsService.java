package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Savings;
import com.example.demo.repository.SavingsRepository;

@Service
public class SavingsService {
	@Autowired
    private SavingsRepository repository;
	
	public List<Savings> getAllSavings(){
		return repository.findAll();
	}
	
	public List<Savings> getSavingsByUserId(Long userId) {
		return repository.findByUserId(userId);
	}
	
	public Savings saveSavings(Savings savings) {
		return repository.save(savings);
	}
	
	public void deleteSavings(Long id) {
		repository.deleteById(id);
	}
}
