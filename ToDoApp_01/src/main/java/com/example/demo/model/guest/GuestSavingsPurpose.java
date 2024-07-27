package com.example.demo.model.guest;

import java.util.UUID;

import lombok.Data;

@Data
public class GuestSavingsPurpose {
	private UUID id;
	private String name;
	private double currentAmount;
	private double neededAmount;
	
	public GuestSavingsPurpose(String name, double currentAmount, double neededAmount) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.currentAmount = currentAmount;
		this.neededAmount = neededAmount;
	}
	
	public void updateCurrentAmount(double addedAmount) {
		this.currentAmount += addedAmount;
	}
	
}
