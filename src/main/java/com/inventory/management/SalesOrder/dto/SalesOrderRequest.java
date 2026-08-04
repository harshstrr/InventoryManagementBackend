package com.inventory.management.SalesOrder.dto;

import com.inventory.management.SalesOrderItem.dto.SalesOrderItemRequest;
import com.inventory.management.SalesOrderItem.modal.SalesOrderItem;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SalesOrderRequest(
   String customerName,
   String customerContact,
   @NotNull(message = "Createdby must not be null")
   Long createdBy,

   @NotNull(message = "Warehouse is required")
   Long warehouseId,

   @NotNull(message = "Order date is required")
   LocalDate orderDate,

   @NotEmpty(message = "At least one item is required")
   List<SalesOrderItemRequest> items
) {}
