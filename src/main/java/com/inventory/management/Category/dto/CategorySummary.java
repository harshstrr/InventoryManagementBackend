package com.inventory.management.Category.dto;

import com.inventory.management.Category.modal.Category;

public record CategorySummary(
        Long id,
        String name,
        Boolean isActive
) {
    public static CategorySummary from(Category category) {
        if (category == null) {
            return null;
        }
        return new CategorySummary(category.getId(), category.getName(), category.getIsActive());
    }
}