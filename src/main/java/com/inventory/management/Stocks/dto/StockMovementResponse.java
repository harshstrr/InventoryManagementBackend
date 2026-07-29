package com.inventory.management.Stocks.dto;

import com.inventory.management.Stocks.modal.StockMovement;

import java.time.LocalDateTime;

public record StockMovementResponse(String type, Integer quantity, String referenceType, Long referenceId, LocalDateTime createdAt) {
    public static StockMovementResponse from(StockMovement m) {
        return new StockMovementResponse(m.getType().name(), m.getQuantity(), m.getReferenceType(), m.getReferenceId(), m.getCreatedAt());
    }
}