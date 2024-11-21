package com.example.demo.model;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MySavingRule {
	@Id
	@SequenceGenerator(name = "my_seq", sequenceName = "my_sequence", initialValue = 10, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
	private Long id;

	private String title;

	private String description;

	private Long userId;

	private Double amount;
	
	@ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(
        name = "MY_SAVING_RULE_FREQUENCY",
        joinColumns = @JoinColumn(name = "MY_SAVING_RULE_ID")
    )
    @Column(name = "DAY_OF_WEEK")
    @Enumerated(EnumType.STRING)
	private Set<DayOfWeek> frequency;

	public boolean isActiveOn(DayOfWeek day) {
		return frequency != null && frequency.contains(day);
	}

	public MySavingRule(String title, String description, Long userId, Double amount, Set<DayOfWeek> frequency) {
		super();
		this.title = title;
		this.description = description;
		this.userId = userId;
		this.amount = amount;
		this.frequency = frequency;
	}

}
