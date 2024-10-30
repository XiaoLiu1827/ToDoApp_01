package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySavingRule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;

	private String description;
	
	private Long userId;
	
	private Double amount;
   
	@OneToMany(mappedBy = "mySavingRule")
	private List<Savings> savings = new ArrayList<>();
	
	public MySavingRule(String title, String description, Long userId, Double amount) {
		super();
		this.title = title;
		this.description = description;
		this.userId = userId;
		this.amount = amount;
	}
	
}
