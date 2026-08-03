package com.inventory.management.PurchaseOrder.dto;

import com.inventory.management.PurchaseOrderItem.dto.ReceiveItemRequest;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ReceiveOrderRequest (
        @NotEmpty List<ReceiveItemRequest> items
) {}
