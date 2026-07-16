package com.inventory.management.Products.dto;

import com.inventory.management.Category.dto.CategorySummary;
import com.inventory.management.Products.modal.Product;

public record ProductResponse (
        Long id,
        String sku,
        String name,
        String description,
        CategorySummary category,
        boolean isActive,
        String unit,
        Double sellingPrice,
        Double costPrice,
        Long reorderThreshold,
        Long reorderQty
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getCategory() != null ? CategorySummary.from(product.getCategory()) : null,
                product.getIsActive(),
                product.getUnit(),
                product.getSellingPrice() != null ? product.getSellingPrice().doubleValue() : null,
                product.getCostPrice() != null ? product.getCostPrice().doubleValue() : null,
                product.getReorderThreshold() != null ? product.getReorderThreshold().longValue() : null,
                product.getReorderQty() != null ? product.getReorderQty().longValue() : null
        );
    }
}