package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class SavingPurpose {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Double currentAmount;
	private Double neededAmount;
	
	public SavingPurpose() {}
	
	public SavingPurpose(String name, Double currentAmount, Double neededAmount) {
		this.name = name;
		this.neededAmount = neededAmount;
		this.currentAmount = currentAmount;
	}
	
	public void updateCurrentAmount(Double addedAmount) {
		this.currentAmount += addedAmount;
	}
}
