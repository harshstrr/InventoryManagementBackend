package com.inventory.management.category.dto;

import com.inventory.management.category.modal.Category;

public record CategorySummary(
        Long id,
        String name
) {
    public static CategorySummary from(Category category) {
        return new CategorySummary(category.getId(), category.getName());
    }
}