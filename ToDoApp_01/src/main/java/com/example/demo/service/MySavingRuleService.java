package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.MySavingRule;
import com.example.demo.repository.MySavingRuleRepository;

@Service
public class MySavingRuleService {
	@Autowired
	MySavingRuleRepository repository;
	
	public List<MySavingRule> getMySavingRuleByUserId(Long userId){
		return repository.findByUserId(userId);
	}
	
	public MySavingRule getMySavingRuleById(Long id) {
		return repository.findById(id).orElse(null);
	}
}