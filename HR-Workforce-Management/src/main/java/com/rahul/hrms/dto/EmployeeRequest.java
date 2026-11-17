package com.rahul.hrms.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EmployeeRequest(
		
		@NotBlank @Size(max = 30) 
		String employeeCode,
		
		@NotBlank @Size(max = 80) 
		String firstName,
		
		@NotBlank @Size(max = 80) 
		String lastName, 
		
		@Email @NotBlank 
		String email, 
		
		@Size(max = 30) 
		String phone,
		
		@NotBlank @Size(max = 100) 
		String jobTitle, 
		
		LocalDate joiningDate, 
		
		Long departmentId) {
}
