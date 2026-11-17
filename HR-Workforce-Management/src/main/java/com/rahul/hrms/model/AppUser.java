package com.rahul.hrms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "app_users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email
	@NotBlank
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank
	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.EMPLOYEE;

	public AppUser() {
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String v) {
		email = v;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String v) {
		password = v;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role v) {
		role = v;
	}
}
