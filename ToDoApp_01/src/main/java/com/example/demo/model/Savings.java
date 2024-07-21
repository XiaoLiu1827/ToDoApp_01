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
public class Savings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
   
    private Double amount;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userAccount_id", referencedColumnName = "id")
	private UserAccount user;
    
	public Savings() {}
	
	public Savings(String name, Double amount, UserAccount user) {
		this.name = name;
		this.amount = amount;
		this.user = user;
	}
}
