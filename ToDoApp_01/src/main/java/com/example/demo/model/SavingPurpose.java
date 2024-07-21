package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userAccount_id", referencedColumnName = "id")
	private UserAccount user;
	
	public SavingPurpose() {}
	
	public SavingPurpose(String name, Double currentAmount, Double neededAmount,UserAccount user) {
		this.name = name;
		this.neededAmount = neededAmount;
		this.currentAmount = currentAmount;
		this.user = user;
	}
	
	public void updateCurrentAmount(Double addedAmount) {
		this.currentAmount += addedAmount;
	}
}
