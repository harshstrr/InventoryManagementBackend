package com.inventory.management.User.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record AppUserRequest (
        @NotNull
        String firstName,

        String lastName,

        String email,

        @NotNull
        BigInteger mobileNumber,

        @NotNull
        String password
) { }
