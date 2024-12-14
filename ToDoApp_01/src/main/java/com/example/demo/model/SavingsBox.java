package com.example.demo.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SavingsBox {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal totalAmount;
	
	@Column(unique = true)
	private Long userId;
	
	public void updateTotalAmount(BigDecimal addedAmount) {
		this.totalAmount = this.totalAmount.add(addedAmount);
	}
}
