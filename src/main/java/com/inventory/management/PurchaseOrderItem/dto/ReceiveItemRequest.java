package com.inventory.management.PurchaseOrderItem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReceiveItemRequest(
        @NotNull Long productId,
        @Positive Integer qtyReceived
) {}
