package com.example.demo.service;

import java.util.Optional;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.form.UserAccountForm;
import com.example.demo.model.UserAccount;

@Service
public class AuthenticationService {
	@Autowired
	private UserAccountService userAccountService;
	
	public UserAccount authenticateUser(UserAccountForm form)throws AuthenticationException {
		Optional<UserAccount> opt = userAccountService.findByUsernameAndPassword(form.getUsername(),
				form.getPassword());
		if (opt.isEmpty()) {
			throw new AuthenticationException();
		}else {
			return opt.get();
		}
	}
}
