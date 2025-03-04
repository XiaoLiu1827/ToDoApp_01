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

    private BigDecimal totalAmount = BigDecimal.ZERO;

	@Column(unique = true)
	private Long userId;

	public BigDecimal updateTotalAmount(BigDecimal addedAmount) {
		this.totalAmount = this.totalAmount.add(addedAmount);
		return this.totalAmount;
	}

	public void withDraw(BigDecimal withdrawalAmount) {
		this.totalAmount = this.totalAmount.subtract(withdrawalAmount);
	}
	
	public String getFormattedAmount() {
		return totalAmount.stripTrailingZeros().toPlainString(); // 整形して返す
	}
}
