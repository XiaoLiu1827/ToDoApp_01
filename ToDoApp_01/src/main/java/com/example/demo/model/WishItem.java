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
public class WishItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Double currentAmount;
	private Double neededAmount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userAccount_id", referencedColumnName = "id")
	private UserAccount user;
	
	public WishItem() {}
	
	public WishItem(String name, Double currentAmount, Double neededAmount) {
		this.name = name;
		this.currentAmount = currentAmount;
		this.neededAmount = neededAmount;
	}
	
	public void updateCurrentAmount(Double addedAmount) {
		this.currentAmount += addedAmount;
	}
}
