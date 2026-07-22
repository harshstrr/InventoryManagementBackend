package com.inventory.management.Login.dto;

import java.math.BigInteger;

public record LoginRequest (
        BigInteger mobileNumber,
        String password
) {
}
