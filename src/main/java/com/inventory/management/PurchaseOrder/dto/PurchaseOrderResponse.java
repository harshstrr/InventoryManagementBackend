package com.inventory.management.PurchaseOrder.dto;

import com.inventory.management.PurchaseOrder.PurchaseOrderStatus;
import com.inventory.management.PurchaseOrder.modal.PurchaseOrder;
import com.inventory.management.PurchaseOrderItem.dto.PurchaseOrderItemResponse;
import com.inventory.management.Supplier.dto.SupplierSummary;
import com.inventory.management.Warehouse.WarehouseSummary;

import java.time.LocalDate;
import java.util.List;

public record PurchaseOrderResponse(
        Long id,
        String poNumber,
        SupplierSummary supplier,
        WarehouseSummary warehouse,
        PurchaseOrderStatus status,
        LocalDate orderDate,
        LocalDate expectedDate,
        CreatedByResponse createdBy,
        List<PurchaseOrderItemResponse> items
) {
    public static PurchaseOrderResponse from(PurchaseOrder po) {
        return new PurchaseOrderResponse(
                po.getId(),
                po.getPoNumber(),
                new SupplierSummary(po.getSupplier().getId(), po.getSupplier().getName()),
                new WarehouseSummary(po.getWarehouse().getId(), po.getWarehouse().getName(), po.getWarehouse().getCode()),
                po.getStatus(),
                po.getOrderDate(),
                po.getExpectedDate(),
                new CreatedByResponse(po.getCreatedBy().getId(), po.getCreatedBy().getFirstName(), po.getCreatedBy().getMobileNumber() ),
                po.getItems().stream().map(PurchaseOrderItemResponse::from).toList()
        );
    }


}
