package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Savings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
   
    private Double amount;
		
	@ManyToOne
	@JoinColumn(name = "my_saving_rule_id", nullable = true) // nullable = true でnullを許可
	private MySavingRule mySavingRule;
	
	private Long userId;

    	
	public Savings(Double amount, MySavingRule mySavingRule, Long userId) {
		this.amount = amount;
		this.mySavingRule = mySavingRule;
		this.userId = userId;
	}
}
