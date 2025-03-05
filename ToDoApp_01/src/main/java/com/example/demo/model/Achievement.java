package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Achievement {
	@Id
	@SequenceGenerator(name = "my_seq", sequenceName = "my_sequence", initialValue = 10, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
	private Long id;
	
	private Boolean isAchieved;
	
	@ManyToOne
	@JoinColumn(name = "my_saving_rule_id", referencedColumnName = "id", nullable = false) 
	private MySavingRule mySavingRule;
	
	private Long userId;
	
    private LocalDate savedDate;

    @Builder
	public Achievement(Boolean isAchieved, MySavingRule mySavingRule,
			Long userId) {
    	this.isAchieved = isAchieved;
		this.mySavingRule = mySavingRule;
		this.userId = userId;
        this.savedDate = LocalDate.now();
	} 
	
}
