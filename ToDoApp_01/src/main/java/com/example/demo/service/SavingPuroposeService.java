package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
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
	
	public List<SavingPurpose>getSavingPurposeByUserId(Long userId) {
		return repository.findByUserId(userId);
	}

	public SavingPurpose updateCurrentAmount(Long purposeId, Double addedAmount) {
		SavingPurpose purpose = repository.findById(purposeId)
				.orElseThrow(() -> new ResourceNotFoundException("SavingPurpose not found with ID: " + purposeId, purposeId));
		purpose.updateCurrentAmount(addedAmount);
		return repository.save(purpose);
	}
	public SavingPurpose saveSavingPurpose(SavingPurpose savingPurpose) {
		return repository.save(savingPurpose);
	}

	public void deleteSavings(Long id) {
		repository.deleteById(id);
	}

}
