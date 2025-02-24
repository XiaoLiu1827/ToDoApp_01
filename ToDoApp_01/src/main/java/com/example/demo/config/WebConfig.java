package com.example.demo.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.UserInterceptor;
import com.example.demo.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthenticationService authService;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserInterceptor(authService))
				.addPathPatterns("/**"); // すべてのリクエストに適用
	}
}
