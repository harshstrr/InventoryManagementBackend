package com.inventory.management.PurchaseOrderItem.dto;

import com.inventory.management.PurchaseOrderItem.modal.PurchaseOrderItem;

import java.math.BigDecimal;

public record PurchaseOrderItemResponse(
        Long productId,
        String productSku,
        String productName,
        Integer qtyOrdered,
        Integer qtyReceived,
        BigDecimal unitPrice
) {
    public static PurchaseOrderItemResponse from(PurchaseOrderItem item) {
        return new PurchaseOrderItemResponse(
                item.getProduct().getId(),
                item.getProduct().getSku(),
                item.getProduct().getName(),
                item.getQtyOrdered(),
                item.getQtyReceived(),
                item.getUnitPrice()
        );
    }
}

