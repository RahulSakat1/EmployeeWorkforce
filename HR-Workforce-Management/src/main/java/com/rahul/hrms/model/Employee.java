package com.rahul.hrms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees", uniqueConstraints = { @UniqueConstraint(columnNames = "email"),
@UniqueConstraint(columnNames = "employeeCode") })
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 30)
	@Column(nullable = false, unique = true)
	private String employeeCode;
	
	@NotBlank
	@Size(max = 80)
	private String firstName;
	
	@NotBlank
	@Size(max = 80)
	private String lastName;
	
	@Email
	@NotBlank
	@Column(nullable = false, unique = true)
	private String email;
	
	@Size(max = 30)
	private String phone;
	
	@NotBlank
	@Size(max = 100)
	private String jobTitle;
	
	private LocalDate joiningDate;
	
	private boolean active = true;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id")
	private Department department;

	public Employee() {
	}

	public Long getId() {
		return id;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String v) {
		employeeCode = v;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String v) {
		firstName = v;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String v) {
		lastName = v;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String v) {
		email = v;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String v) {
		phone = v;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String v) {
		jobTitle = v;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate v) {
		joiningDate = v;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean v) {
		active = v;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department v) {
		department = v;
	}
}
