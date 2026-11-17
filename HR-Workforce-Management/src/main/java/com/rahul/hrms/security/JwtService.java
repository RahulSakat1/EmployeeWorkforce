package com.rahul.hrms.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

	private final SecretKey key;

	private final long expirationMs;

	public JwtService(@Value("${security.jwt.secret}") String secret,
			@Value("${security.jwt.expiration-ms}") long expirationMs) {
		
		try {
		
			this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
		} 
		catch (IllegalArgumentException ex) {
		
			throw new IllegalArgumentException(
					"security.jwt.secret must be a Base64-encoded secret of at least 32 bytes", ex);
		}
		this.expirationMs = expirationMs;
	}

	public String generateToken(String email, String role) {
		
		Date now = new Date();
		
		return Jwts.builder().subject(email).claim("role", role).issuedAt(now)
				.expiration(new Date(now.getTime() + expirationMs)).signWith(key).compact();
	}

	public String extractEmail(String token) {
		
		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
		
	}

	public String extractRole(String token) {
		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("role", String.class);
	}

	public boolean isValid(String token) {
		
		try {
		
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
			
			return true;
		}
		catch (RuntimeException ex) {
		
			return false;
		}
	}
}
