package com.rahul.hrms.service;

import com.rahul.hrms.dto.*;
import com.rahul.hrms.model.AppUser;
import com.rahul.hrms.repository.UserRepository;
import com.rahul.hrms.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
	private final UserRepository users;
	private final PasswordEncoder encoder;
	private final JwtService jwt;

	public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwt) {
		this.users = users;
		this.encoder = encoder;
		this.jwt = jwt;
	}

	public void register(RegisterRequest request) {
		if (users.existsByEmailIgnoreCase(request.email()))
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
		AppUser u = new AppUser();
		u.setEmail(request.email().trim().toLowerCase());
		u.setPassword(encoder.encode(request.password()));
		u.setRole(request.role());
		users.save(u);
	}

	public AuthResponse login(LoginRequest request) {
		AppUser u = users.findByEmailIgnoreCase(request.email())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
		
		if (!encoder.matches(request.password(), u.getPassword()))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
	
		return new AuthResponse(jwt.generateToken(u.getEmail(), u.getRole().name()), "Bearer", u.getEmail(),
				u.getRole().name());
	}
}
