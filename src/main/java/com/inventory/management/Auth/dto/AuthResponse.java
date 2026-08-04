package com.inventory.management.Auth.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}