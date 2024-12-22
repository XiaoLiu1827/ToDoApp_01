package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.MySavingRule;

@Repository
public interface MySavingRuleRepository extends JpaRepository<MySavingRule, Long>{
	List<MySavingRule> findByUserId(Long userId);
}
