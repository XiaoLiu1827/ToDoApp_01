package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.auth.CustomUserDetails;

@Service
public class AuthenticationService {
	@Autowired
	private UserAccountService userAccountService;

	//	public UserAccount authenticateUser(UserAccountForm form)throws AuthenticationException {
	//		Optional<UserAccount> opt = userAccountService.findByUsernameAndPassword(form.getUsername(),
	//				form.getPassword());
	//		if (opt.isEmpty()) {
	//			throw new AuthenticationException();
	//		}else {
	//			return opt.get();
	//		}
	//	}

	public Long getAuthenticatedUserId() {

		//リクエストごとにSecurityContextを取得する
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		boolean isAuth = true;
		if (authentication == null) {
			isAuth = false;
		}

		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof CustomUserDetails) {
				return ((CustomUserDetails) principal).getId(); // カスタムUserDetailsからIDを取得
			}
		}
		return null; // 認証されていない場合
	}

	public String getAutenticatedUsename() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return authentication != null ? authentication.getName() : null;
	}
}
