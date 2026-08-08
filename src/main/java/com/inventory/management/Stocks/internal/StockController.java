package com.inventory.management.Stocks.internal;
import com.inventory.management.Common.ApiResponse;
import com.inventory.management.Products.modal.Product;
import com.inventory.management.Products.repository.ProductRepository;
import com.inventory.management.Stocks.StockServices;
import com.inventory.management.Stocks.dto.StockAdjustRequest;
import com.inventory.management.Stocks.dto.StockItemResponse;
import com.inventory.management.Stocks.dto.StockMovementResponse;
import com.inventory.management.Stocks.modal.StockItem;
import com.inventory.management.Stocks.modal.StockMovement;
import com.inventory.management.Stocks.repository.StockItemRepository;
import com.inventory.management.Stocks.repository.StockMovementRepository;
import com.inventory.management.User.modal.AppUser;
import com.inventory.management.User.repository.AppUserRepository;
import com.inventory.management.Warehouse.modal.Warehouse;
import com.inventory.management.Warehouse.repository.WarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/stock")
public class StockController {

    private final StockServices stockServices;
    private final StockItemRepository stockItemRepository;
    private final StockMovementRepository stockMovementRepository;


    @GetMapping
    public ResponseEntity<ApiResponse<List<StockItemResponse>>> getStock(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(defaultValue = "false") boolean lowStockOnly) {

        List<StockItem> items;

        if (lowStockOnly) {
            items = stockItemRepository.findLowStockItems();
        } else if (productId != null && warehouseId == null) {
            items = stockItemRepository.findByProductId(productId);
        } else {
            items = stockItemRepository.findByWarehouseId(warehouseId);
        }

        List<StockItemResponse> response = items.stream().map(StockItemResponse::from).toList();
        return ResponseEntity.ok(ApiResponse.success(response, "Successfully fetched All Stock Items"));
    }

    @GetMapping("/movements")
    public ResponseEntity<ApiResponse<Page<StockMovementResponse>>> getMovements(
            @RequestParam Long productId,
            @RequestParam Long warehouseId,
            Pageable pageable) {

        Page<StockMovement> movements = stockMovementRepository
                .findByProductIdAndWarehouseId(productId, warehouseId, pageable);

        return ResponseEntity.ok(ApiResponse.success(movements.map(StockMovementResponse::from) , "Successfully fetched Stocks"));
    }


    @PostMapping("/adjustments")
    public ResponseEntity<ApiResponse<Void>> adjustments (@RequestBody StockAdjustRequest req) {
        try{
            stockServices.adjustStock(req.productId(), req.fromWarehouseId(), req.toWarehouseId(), req.deltaQty(), req.note(), req.createdBy());

            return ResponseEntity.status(201).body(ApiResponse.success(null , "Successfully stock is Adjusted"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

}