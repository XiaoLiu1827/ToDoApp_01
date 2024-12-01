package com.example.demo.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MySavingRuleDto;
import com.example.demo.model.MySavingRule;
import com.example.demo.service.MySavingRuleService;

@RestController
@RequestMapping("/savings/api/mySavingRule")
public class SavingRuleApiController {
	 @Autowired
	    private MySavingRuleService mySavingRuleService;
	@PostMapping("/update/{id}")
	public ResponseEntity<MySavingRule> updateMySavingRule(
			@PathVariable Long id,
			@RequestBody MySavingRuleDto updatedRule) {
		//既存のエンティティを取得
		MySavingRule entity = mySavingRuleService.getMySavingRuleById(id);
		//DTOからエンティティに反映
		MySavingRule result = mySavingRuleService.updateMySavingRuleFromDto(entity, updatedRule);
		// 更新後のオブジェクトを JSON 形式で返す
		return ResponseEntity.ok(result);
	}
}
