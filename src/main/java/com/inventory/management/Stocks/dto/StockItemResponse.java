package com.inventory.management.Stocks.dto;

import com.inventory.management.Stocks.modal.StockItem;

public record StockItemResponse(Long productId, String productSku, Long warehouseId, Integer quantity) {
    public static StockItemResponse from(StockItem item) {
        return new StockItemResponse(item.getProduct().getId(), item.getProduct().getSku(),
                item.getWarehouse().getId(), item.getQuantity());
    }
}