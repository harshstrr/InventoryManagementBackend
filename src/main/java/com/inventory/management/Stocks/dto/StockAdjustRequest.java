package com.inventory.management.Stocks.dto;

import jakarta.validation.constraints.NotNull;

public record StockAdjustRequest(
       @NotNull(message = "ProductId can't be null")  Long productId,
       @NotNull(message = "From Warehouse id can't be null") Long fromWarehouseId,
       @NotNull(message = "To Warehouse id can't be null") Long toWarehouseId,

       @NotNull(message = "DeltaQty can't be null") int deltaQty,
       @NotNull(message = "Must add a short note") String note,
       @NotNull(message = "CreatedBy must not be null") Long createdBy


) {}
