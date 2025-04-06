package com.example.demo.service;

import java.math.BigDecimal;
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

	//トランザクション管理のアドバイスもらう
	//呼び出し元で取得したitemの扱い
	public void setItemStatus(BigDecimal updatedTotalAmount, WishItem selectedItem) {
		
		if (updatedTotalAmount.compareTo(BigDecimal.ZERO) < 0) {
			selectedItem.setStatus(2);
		} else {
			selectedItem.setStatus(1);
		}
	}

	/**検索**/
	public List<WishItem> getAllSavingPurposes() {
		return repository.findAll();
	}

	public WishItem getWishItembyId(Long wishItemId) {
		return repository.findById(wishItemId).orElse(null);
	}

	public List<WishItem> getSavingPurposeByUserId(Long userId) {
		return repository.findByUserId(userId);
	}

	public WishItem updateCurrentAmount(Long wishItemId, BigDecimal addedAmount) {
		WishItem wishItem = repository.findById(wishItemId)
				.orElseThrow(() -> new ResourceNotFoundException("SavingPurpose not found with ID: " + wishItemId));
		wishItem.updateCurrentAmount(addedAmount);
		return repository.save(wishItem);
	}

	public WishItem saveWishItem(WishItem savingWishItem) {
		return repository.save(savingWishItem);
	}

	public void deleteWishItem(Long id) {
		repository.deleteById(id);
	}

}
