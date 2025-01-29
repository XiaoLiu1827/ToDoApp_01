package com.example.demo.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.SessionExpiredException;
import com.example.demo.model.SavingsBox;
import com.example.demo.model.UserAccount;
import com.example.demo.model.WishItem;
import com.example.demo.repository.SavingsBoxRepository;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.util.MessageUtils;

@Service
public class UserAccountService {
	@Autowired
	private UserAccountRepository userAccountRepository;
	@Autowired
	SavingsBoxRepository savingsBoxRepository;
	@Autowired
	private MessageUtils messageUtils;

	public Optional<UserAccount> findByUsernameAndPassword(String username, String password) {
		return userAccountRepository.findByUsernameAndPassword(username, password);
	};

	public void addWishItem(Long userId, String name, BigDecimal neededAmount, String imagePath) {
		UserAccount user = findById(userId);

		WishItem item = new WishItem(name, BigDecimal.ZERO, neededAmount, imagePath);
		user.addPurpose(item);

		userAccountRepository.save(user);
	}

	public UserAccount findById(Long id) {
		Optional<UserAccount> opt = userAccountRepository.findById(id);

		if (opt.isEmpty()) {
			throw new SessionExpiredException(messageUtils.get("session.expired"));
		} else {
			return opt.get();
		}
	}

	public UserAccount saveUser(UserAccount user) {
		UserAccount savedUser = userAccountRepository.save(user);
		SavingsBox savingsBox = new SavingsBox();
		savingsBox.setUserId(savedUser.getId());
		savingsBoxRepository.save(savingsBox);
		savedUser.setSavingsBox(savingsBox);
		return userAccountRepository.save(savedUser);
	}
}
