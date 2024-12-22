package com.example.demo.form;

import java.math.BigDecimal;
import java.util.Set;

import com.example.demo.model.DayOfWeek;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MySavingRuleForm {
	@NotNull
	private String title;

	private String description;
    @DecimalMin(value = "0.0", inclusive = false, message = "amount must be greater than zero")
	private BigDecimal amount;
    private Set<DayOfWeek> frequency;
    
    public boolean isActiveOn(DayOfWeek day) {
    	return frequency != null && frequency.contains(day);
    }

}
