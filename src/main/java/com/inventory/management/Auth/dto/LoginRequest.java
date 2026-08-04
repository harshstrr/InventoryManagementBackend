package com.inventory.management.Auth.dto;

import java.math.BigInteger;

public record LoginRequest (
        BigInteger mobileNumber,
        String password
) {
}
