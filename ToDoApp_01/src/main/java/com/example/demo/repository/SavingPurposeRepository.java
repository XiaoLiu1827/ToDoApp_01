package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.SavingPurpose;

@Repository
public interface SavingPurposeRepository extends JpaRepository<SavingPurpose, Long>{
	
}
