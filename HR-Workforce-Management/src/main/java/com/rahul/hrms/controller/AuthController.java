package com.rahul.hrms.controller;
import com.rahul.hrms.dto.*;
import com.rahul.hrms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService auth;
    public AuthController(AuthService auth){this.auth=auth;}
    @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) public String register(@Valid @RequestBody RegisterRequest r){auth.register(r);return "User registered successfully";}
    @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r){return auth.login(r);}
}
