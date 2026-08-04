package com.inventory.management.SalesOrderItem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SalesOrderItemRequest(
        @NotNull(message = "Product is required")
        Long productId,

        @Positive(message = "Quantity must be positive value")
        Integer qty,

        @NotNull
        @Positive(message = "Price must be positive value")
        BigDecimal unitPrice

) {}
