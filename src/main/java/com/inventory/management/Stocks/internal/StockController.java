package com.inventory.management.Stocks.internal;
import com.inventory.management.Common.ApiResponse;
import com.inventory.management.Stocks.dto.StockItemResponse;
import com.inventory.management.Stocks.dto.StockMovementResponse;
import com.inventory.management.Stocks.modal.StockItem;
import com.inventory.management.Stocks.modal.StockMovement;
import com.inventory.management.Stocks.repository.StockItemRepository;
import com.inventory.management.Stocks.repository.StockMovementRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockItemRepository stockItemRepository;
    private final StockMovementRepository stockMovementRepository;

    public StockController(StockItemRepository stockItemRepository,
                           StockMovementRepository stockMovementRepository) {
        this.stockItemRepository = stockItemRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    // GET /api/v1/stock?productId=5          -> stock for one product, all warehouses
    // GET /api/v1/stock?warehouseId=2        -> everything in one warehouse
    // GET /api/v1/stock?lowStockOnly=true    -> only items below reorder threshold
    @GetMapping
    public ResponseEntity<ApiResponse<List<StockItemResponse>>> getStock(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long warehouseId,
            @RequestParam(defaultValue = "false") boolean lowStockOnly) {

        List<StockItem> items;
        if (lowStockOnly) {
            items = stockItemRepository.findLowStockItems();
        } else if (productId != null) {
            items = stockItemRepository.findByProductId(productId);
        } else {
            items = stockItemRepository.findByWarehouseId(warehouseId);
        }

        List<StockItemResponse> response = items.stream().map(StockItemResponse::from).toList();
        return ResponseEntity.ok(ApiResponse.success(response, "Successfully fetched All Stock Items"));
    }

    // GET /api/v1/stock/movements?productId=5&warehouseId=2
    @GetMapping("/movements")
    public ResponseEntity<ApiResponse<Page<StockMovementResponse>>> getMovements(
            @RequestParam Long productId,
            @RequestParam Long warehouseId,
            Pageable pageable) {

        Page<StockMovement> movements = stockMovementRepository
                .findByProductIdAndWarehouseId(productId, warehouseId, pageable);

        return ResponseEntity.ok(ApiResponse.success(movements.map(StockMovementResponse::from) , "Successfully fetched Stocks"));
    }
}