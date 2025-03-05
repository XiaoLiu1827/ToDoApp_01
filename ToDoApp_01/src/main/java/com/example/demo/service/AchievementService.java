package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Achievement;
import com.example.demo.model.MySavingRule;
import com.example.demo.repository.AchievementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AchievementService {

	private final AchievementRepository repository;

	public Achievement saveAchievement(Boolean isAchieved, MySavingRule rule, Long userId) {
		Achievement achievement = Achievement.builder()
				.isAchieved(isAchieved)
				.mySavingRule(rule)
				.userId(userId)
				.build();

		return repository.save(achievement);

	}
}
