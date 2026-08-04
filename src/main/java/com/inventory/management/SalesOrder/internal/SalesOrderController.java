package com.inventory.management.SalesOrder.internal;

import com.inventory.management.Common.ApiResponse;
import com.inventory.management.SalesOrder.SalesOrderService;
import com.inventory.management.SalesOrder.dto.SalesOrderRequest;
import com.inventory.management.SalesOrder.modal.SalesOrder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/sales")
public class SalesOrderController {
    private final SalesOrderService so_service;

    @PostMapping
    public ResponseEntity<ApiResponse<SalesOrder>> createSale (@RequestBody @Valid SalesOrderRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success(so_service.createSales(request) , "Successfully created Sale record"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SalesOrder>>> getAllSales () {
        return ResponseEntity.ok(ApiResponse.success(so_service.getAll() , "Successfully fetched all sales records"));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<SalesOrder>> cancelSale(@PathVariable Long id) {
        return ResponseEntity.status(201).body(ApiResponse.success(so_service.cancelOrder(id) , "Successfully fetched all sales records"));
    }
}

