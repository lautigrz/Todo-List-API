package com.app.controller;

import com.app.config.jwt.JwtUtils;
import com.app.models.RefreshToken;
import com.app.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public AuthController(JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
        RefreshToken refresh = refreshTokenService.findByToken(refreshToken);

        if(refresh.getExpiresAt().isBefore(Instant.now())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", "Refresh token expired"));
        }

        String token = jwtUtils.generateToken(refresh.getUser().getUsername());

        return ResponseEntity.ok(Map.of(
                "token", token
        ));

    }

}
