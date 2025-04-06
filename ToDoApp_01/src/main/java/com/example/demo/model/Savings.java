package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
   
    private BigDecimal amount;
		
	@ManyToOne
	@JoinColumn(name = "my_saving_rule_id", referencedColumnName = "id", nullable = true) // nullable = true でnullを許可
	private MySavingRule mySavingRule;
	
	private Long userId;

    private LocalDate savedDate;
    
    @Builder
	public Savings(BigDecimal amount, MySavingRule mySavingRule,
			Long userId) {
		this.amount = amount;
		this.mySavingRule = mySavingRule;
		this.userId = userId;
        this.savedDate = LocalDate.now();
	} 
    
    @Override
    public String toString() {
        return "Savings{id=" + id + 
               ", amount=" + amount + 
               ", mySavingsRule=" + mySavingRule.getId() +
               ", userId=" + userId + 
               ", savedDate=" + savedDate + 
               '}';
    }
}
