package com.inventory.management.Products.dto;

import com.inventory.management.Category.dto.CategorySummary;
import com.inventory.management.Category.modal.Category;
import com.inventory.management.Products.modal.Product;

public record ProductSummaryResponse(
        Long id,
        String sku,
        String name,
        String description,
        CategorySummary category,
        Boolean isActive
) {
    public static ProductSummaryResponse from(Product product) {
        if(product == null) {
            return null;
        }
        return new ProductSummaryResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                new CategorySummary(
                        product.getCategory().getId(),
                        product.getCategory().getName(),
                        product.getCategory().getIsActive()
                ),
                product.getIsActive()
        );
    }
}
