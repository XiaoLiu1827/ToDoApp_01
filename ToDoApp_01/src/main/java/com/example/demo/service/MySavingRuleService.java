package com.example.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

	public Optional<MySavingRule> getMySavingRuleById(Long id) {
		return repository.findById(id);
	}

	public MySavingRule saveMySavingRule(MySavingRule myRule) {
		return repository.save(myRule);
	}

	public MySavingRule updateMySavingRuleFromDto(MySavingRule entity, MySavingRuleDto dto) {
		String[] nullNames = getNullPropertyNames(dto);
		BeanUtils.copyProperties(dto, entity, nullNames);
		return repository.save(entity);      
	}
	
	public boolean deleteMySavingRule(Long id) {
		if(repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}
	
	private String[] getNullPropertyNames(Object source) {
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