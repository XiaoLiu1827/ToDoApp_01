package com.example.demo.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.SessionExpiredException;
import com.example.demo.model.UserAccount;
import com.example.demo.model.WishItem;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.util.MessageUtils;

@Service
public class UserAccountService {
	@Autowired
	private UserAccountRepository repository;
	@Autowired
	private MessageUtils messageUtils;
	
    public Optional<UserAccount> findByUsernameAndPassword(String username, String password){
    	return repository.findByUsernameAndPassword(username, password);
    };
    
    public void addWishItem(Long userId,String name, BigDecimal neededAmount) {
    	UserAccount user = findById(userId);
    	
    	WishItem purpose = new WishItem(name, BigDecimal.ZERO, neededAmount);
		user.addPurpose(purpose);
		
		repository.save(user);
    }
    
    public UserAccount findById(Long id){
    	Optional<UserAccount> opt = repository.findById(id);
    	
    	if (opt.isEmpty()) {
			throw new SessionExpiredException(messageUtils.get("session.expired"));
		}else {
			return opt.get();
		}
    }
}
