package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.WishItem;
import com.example.demo.repository.WishItemRepository;

@Service
public class WishItemService {
	@Autowired
	private WishItemRepository repository;

	/**検索**/
	public List<WishItem> getAllSavingPurposes() {
		return repository.findAll();
	}

	public WishItem getSavingPurposeById(Long wishItemId) {
		return repository.findById(wishItemId).orElse(null);
	}

	public List<WishItem> getSavingPurposeByUserId(Long userId) {
		return repository.findByUserId(userId);
	}

	public WishItem updateCurrentAmount(Long wishItemId, Double addedAmount) {
		WishItem purpose = repository.findById(wishItemId)
				.orElseThrow(() -> new ResourceNotFoundException("SavingPurpose not found with ID: " + wishItemId));
		purpose.updateCurrentAmount(addedAmount);
		return repository.save(purpose);
	}

	public WishItem saveSavingPurpose(WishItem savingWishItem) {
		return repository.save(savingWishItem);
	}

	public void deleteSavings(Long id) {
		repository.deleteById(id);
	}

}
