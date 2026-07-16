package com.inventory.management.Category.dto;

import jakarta.validation.constraints.*;



public record UpdateCategoryRequest (
        @NotBlank(message = "Name is required")
        String name,

        Long parent_id
) {}
