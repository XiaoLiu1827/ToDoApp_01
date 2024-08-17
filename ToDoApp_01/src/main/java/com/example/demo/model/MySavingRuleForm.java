package com.example.demo.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MySavingRuleForm {
	@NotNull
	private String name;

	private String Description;
    @DecimalMin(value = "0.0", inclusive = false, message = "amount must be greater than zero")
	private Double defaultSavingsAmount;


}
