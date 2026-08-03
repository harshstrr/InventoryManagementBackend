package com.inventory.management.PurchaseOrder.dto;

import com.inventory.management.PurchaseOrder.modal.PurchaseOrder;
import com.inventory.management.PurchaseOrderItem.dto.PurchaseOrderItemRequest;
import com.inventory.management.PurchaseOrderItem.modal.PurchaseOrderItem;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record PurchaseOrderRequest(
        @NotNull Long supplierId,
        @NotNull Long warehouseId,
        @NotNull LocalDate orderDate,
        LocalDate expectedDate,
        @NotNull Long createdBy,
        @NotEmpty List<PurchaseOrderItemRequest> items
) {}
