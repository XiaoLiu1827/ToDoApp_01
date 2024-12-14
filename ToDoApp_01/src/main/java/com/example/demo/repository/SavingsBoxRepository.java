package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.SavingsBox;

@Repository
public interface SavingsBoxRepository extends JpaRepository<SavingsBox, Long>{
	SavingsBox findByUserId(Long userId);
}
