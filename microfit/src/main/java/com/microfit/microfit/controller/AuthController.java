package com.microfit.microfit.controller;

import com.microfit.microfit.dto.LoginRequest;
import com.microfit.microfit.dto.LoginResponse;
import com.microfit.microfit.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public LoginResponse register() {

        return new LoginResponse(
                "User Registered Successfully");
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        if ("admin@gmail.com".equals(request.getEmail())
                && "admin123".equals(request.getPassword())) {

            String token =
                    jwtUtil.generateToken(request.getEmail());

            return new LoginResponse(token);
        }

        return new LoginResponse(
                "Invalid Credentials");
    }
}