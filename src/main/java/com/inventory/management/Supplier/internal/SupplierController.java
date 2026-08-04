package com.inventory.management.Supplier.internal;

import com.inventory.management.Common.ApiResponse;
import com.inventory.management.Supplier.SupplierServices;
import com.inventory.management.Supplier.dto.CreateSupplierRequest;
import com.inventory.management.Supplier.dto.SupplierResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/supplier")
public class SupplierController {
    private SupplierServices supplierServices;

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierResponse>> addSupplier (@RequestBody @Valid CreateSupplierRequest s) {
        try {
            return ResponseEntity.ok(ApiResponse.success(SupplierResponse.from(supplierServices.addSupplier(s)) , "Successfully added new Supplier"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierResponse>>> getAllSupplier () {
        try {
            List<SupplierResponse> suppliers = supplierServices.allSuppliers()
                    .stream()
                    .map(SupplierResponse::from)
                    .toList();

            return ResponseEntity.ok(ApiResponse.success(suppliers , "Successfully fetched Suppliers" ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
