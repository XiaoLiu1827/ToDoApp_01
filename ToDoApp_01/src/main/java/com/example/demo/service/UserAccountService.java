package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;

@Service
public class UserAccountService {
	@Autowired
	private UserAccountRepository repository;
	
    public Optional<UserAccount> findByUsernameAndPassword(String username, String password){
    	return repository.findByUsernameAndPassword(username, password);
    };
    public Optional<UserAccount> findById(Long id){
    	return repository.findById(id);
    }
}
