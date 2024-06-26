package com.petopia.petopia.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String extractEmail(String token);

    String generateAccessToken(UserDetails user);

    String generateRefreshToken(UserDetails user);

    boolean checkTokenIsValid(String token);
}
