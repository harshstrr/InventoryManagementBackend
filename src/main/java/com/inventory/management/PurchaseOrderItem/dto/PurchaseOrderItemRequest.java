package com.inventory.management.PurchaseOrderItem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PurchaseOrderItemRequest(
        @NotNull Long productId,
        @Positive Integer qtyOrdered,
        @NotNull @Positive BigDecimal unitPrice
) {}
