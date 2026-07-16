package com.inventory.management.Warehouse.internal;


import com.inventory.management.Common.ApiResponse;
import com.inventory.management.Warehouse.WarehouseServices;
import com.inventory.management.Warehouse.modal.Warehouse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseServices warehouseServices;

    @GetMapping("/getAllWarehouse")
    public ResponseEntity<ApiResponse<List<Warehouse>>> getAllWarehouse () {
        try {
            return ResponseEntity.ok(ApiResponse.success(warehouseServices.getAllWarehouse() , "Successfully fetched Warehouses"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/add-warehouse")
    public ResponseEntity<ApiResponse<Warehouse>> addWarehouse (@RequestBody @Valid Warehouse w) {
        try {
            return ResponseEntity.ok(ApiResponse.success(warehouseServices.addWarehouse(w) , "Successfully Added Warehouse"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }


}
