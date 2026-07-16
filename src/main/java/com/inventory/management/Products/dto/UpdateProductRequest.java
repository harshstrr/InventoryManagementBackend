package com.inventory.management.Products.dto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 200)
        String name,

        String description,

        @NotNull(message = "Category is required")
        Long categoryId,

        @NotBlank
        String unit,

        @NotNull
        @Positive(message = "Cost price must be positive")
        BigDecimal costPrice,

        @NotNull
        @Positive(message = "Selling price must be positive")
        BigDecimal sellingPrice,

        @Min(0)
        Integer reorderThreshold,

        @Min(0)
        Integer reorderQty
) {}