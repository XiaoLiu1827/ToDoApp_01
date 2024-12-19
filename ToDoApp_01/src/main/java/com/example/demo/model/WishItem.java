package com.example.demo.model;

import java.math.BigDecimal;

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
	private BigDecimal currentAmount;
	private BigDecimal neededAmount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userAccount_id", referencedColumnName = "id")
	private UserAccount user;
	
	public WishItem() {}
	
	public WishItem(String name, BigDecimal currentAmount, BigDecimal neededAmount) {
		this.name = name;
		this.currentAmount = currentAmount;
		this.neededAmount = neededAmount;
	}
	
	public void updateCurrentAmount(BigDecimal addedAmount) {
	    if (addedAmount != null) {
	        this.currentAmount = this.currentAmount.add(addedAmount);
	    } else {
	        throw new IllegalArgumentException("Added amount cannot be null");
	    }
	}

	public boolean checkProgress() {
	    return this.currentAmount.compareTo(this.neededAmount) >= 0;
	}
	
	public String getFormattedAmount() {
		return neededAmount.stripTrailingZeros().toPlainString(); // 整形して返す
	}

}
