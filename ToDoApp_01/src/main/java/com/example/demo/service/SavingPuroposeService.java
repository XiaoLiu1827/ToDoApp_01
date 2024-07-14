package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.SavingPurpose;
import com.example.demo.repository.SavingPurposeRepository;

@Service
public class SavingPuroposeService {
	@Autowired
	private SavingPurposeRepository repository;

	/**検索**/
	public List<SavingPurpose> getAllSavingPurposes() {
		return repository.findAll();
	}

	public SavingPurpose getSavingPurposeById(Long purposeId) {
		return repository.findById(purposeId).orElse(null);
	}

	public SavingPurpose saveSavingPurpose(SavingPurpose savingPurpose) {
		return repository.save(savingPurpose);
	}

	public void deleteSavings(Long id) {
		repository.deleteById(id);
	}

}
