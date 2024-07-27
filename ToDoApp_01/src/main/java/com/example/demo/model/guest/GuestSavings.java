package com.example.demo.model.guest;

import lombok.Data;
@Data
public class GuestSavings {
	private String name;
    private Double amount;
    
	public GuestSavings(String name, Double amount) {
		this.name = name;
		this.amount = amount;
	}
	
}
