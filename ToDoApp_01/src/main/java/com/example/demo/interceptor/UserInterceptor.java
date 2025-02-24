package com.example.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserInterceptor implements HandlerInterceptor {

	private final AuthenticationService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Long userId = authService.getAuthenticatedUserId();
		String username = authService.getAutenticatedUsename();
		if (userId != null) {
			request.setAttribute("userId", userId);
		}
		if(username != null) {
			request.setAttribute("username", username);
		}
		return true; // 次のインターセプターまたはコントローラーに処理を渡す
	}

}
