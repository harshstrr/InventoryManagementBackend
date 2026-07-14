package com.inventory.management.category.dto;

import jakarta.validation.constraints.*;



public record UpdateCategoryRequest (
        @NotBlank(message = "Name is required")
        String name,

        Integer parent_id
) {}
