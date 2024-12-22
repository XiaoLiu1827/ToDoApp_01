package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MySavingRuleDto;
import com.example.demo.model.MySavingRule;
import com.example.demo.repository.MySavingRuleRepository;

@Service
public class MySavingRuleService {
	@Autowired
	MySavingRuleRepository repository;

	public List<MySavingRule> getMySavingRuleByUserId(Long userId) {
		return repository.findByUserId(userId);
	}

	public MySavingRule getMySavingRuleById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public MySavingRule saveMySavingRule(MySavingRule myRule) {
		return repository.save(myRule);
	}

	public MySavingRule updateMySavingRuleFromDto(MySavingRule entity, MySavingRuleDto dto) {
		BeanUtils.copyProperties(dto, entity, getNullPropertyNames(dto));
		return repository.save(entity);      
	}

	public String[] getNullPropertyNames(Object source) {
		return Arrays.stream(BeanUtils.getPropertyDescriptors(source.getClass()))
				.map(pd -> pd.getName()).filter(name -> {
					try {
						return BeanUtils.getPropertyDescriptor(source.getClass(), name)
								.getReadMethod().invoke(source) == null;
					}catch(Exception e) {
						return true;
					}
				})
				.toArray(String[]::new);
	}
}