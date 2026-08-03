package com.inventory.management.PurchaseOrder.internal;

import com.inventory.management.Common.ApiResponse;
import com.inventory.management.PurchaseOrder.PurchaseOrderServices;
import com.inventory.management.PurchaseOrder.dto.PurchaseOrderRequest;
import com.inventory.management.PurchaseOrder.dto.PurchaseOrderResponse;
import com.inventory.management.PurchaseOrder.dto.ReceiveOrderRequest;
import com.inventory.management.PurchaseOrder.modal.PurchaseOrder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class PurchaseOrderController {

    private PurchaseOrderServices purchaseOrderServices;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseOrderResponse>>> getAllOrders () {
        try {
            List<PurchaseOrderResponse> orders = purchaseOrderServices.getAll().stream().map((PurchaseOrderResponse :: from)).toList();
            return ResponseEntity.ok(ApiResponse.success(orders , "Successfully Fetched Purchased Orders"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/purchase")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> createPurchase (@RequestBody @Valid PurchaseOrderRequest request) {
         return ResponseEntity.status(201).body(ApiResponse.success(PurchaseOrderResponse.from(purchaseOrderServices.createOrder(request)) , "Successfully Ordered Items" ));

    }

    @PostMapping("/{id}/receive")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> receiveOrder(@PathVariable Long id , @RequestBody @Valid ReceiveOrderRequest request) {
        try {
            return ResponseEntity.status(201).body(ApiResponse.success(PurchaseOrderResponse.from(purchaseOrderServices.receiveOrder(id, request)) , "Successfully Added Received Order"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<PurchaseOrderResponse>> cancelOrder(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ApiResponse.success(PurchaseOrderResponse.from(purchaseOrderServices.cancelOrder(id)) , "Successfully Cancel Order"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }

    }
}
