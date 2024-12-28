package com.example.demo.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MySavingRuleDto {
	private String description;

	private BigDecimal amount;
}
