package com.example.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.example.demo.domain.auth.CustomUserDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final HttpSessionSecurityContextRepository securityContextRepository
     = new HttpSessionSecurityContextRepository();
    
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

	//手動で認証情報を作成する
	public void authenticateUser(String username, String password,
			HttpServletRequest request, HttpServletResponse response) {
		// 認証トークンの作成
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		
	    Authentication authentication = authenticationManager.authenticate(authToken);

	    SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

		// SecurityContext にセット
        securityContextRepository.saveContext(context, request, response);
	
	}

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
