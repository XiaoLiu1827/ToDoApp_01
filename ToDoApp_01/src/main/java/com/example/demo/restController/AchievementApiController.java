package com.example.demo.restController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Achievement;
import com.example.demo.model.MySavingRule;
import com.example.demo.service.AchievementService;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.MySavingRuleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/savings/api/achievement")
@RequiredArgsConstructor
public class AchievementApiController {

	private final AuthenticationService authService;
	
	private final MySavingRuleService mySavingRuleService;

	private final AchievementService achievementService;

	private Long userId;

	@ModelAttribute
	public void setUser() {
		this.userId = authService.getAuthenticatedUserId();
	}

	@PostMapping("/{ruleId}")
	public ResponseEntity<String> recordAchievement(@PathVariable Long ruleId,
			@RequestParam boolean achieved) {
		MySavingRule myRule = mySavingRuleService.getMySavingRuleById(ruleId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "貯金ルールが見つかりません"));

		Achievement savedAchievement = achievementService.saveAchievement(achieved, myRule, userId);
		
		return ResponseEntity.ok("達成状況を記録しました");
		//return ResponseEntity.badRequest().body("エラー: 達成状況の記録に失敗しました");

	}
}
