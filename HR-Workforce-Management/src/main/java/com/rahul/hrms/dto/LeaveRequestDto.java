package com.rahul.hrms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record LeaveRequestDto(
		
		@NotNull 
		Long employeeId, 
		
		@NotNull 
		LocalDate startDate, 
		
		@NotNull 
		LocalDate endDate,
		
		@Size(max = 1000) 
		String reason) {
}
