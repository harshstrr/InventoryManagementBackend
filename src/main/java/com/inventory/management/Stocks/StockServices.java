package com.inventory.management.Stocks;

import com.inventory.management.Products.modal.Product;
import com.inventory.management.Stocks.modal.MovementType;
import com.inventory.management.Stocks.modal.StockItem;
import com.inventory.management.Stocks.modal.StockMovement;
import com.inventory.management.Stocks.repository.StockItemRepository;
import com.inventory.management.Stocks.repository.StockMovementRepository;
import com.inventory.management.Warehouse.modal.Warehouse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockServices {

    private final StockItemRepository stockItemRepository;
    private final StockMovementRepository stockMovementRepository;


    public StockServices(StockItemRepository stockItemRepository, StockMovementRepository stockMovementRepository) {
        this.stockItemRepository = stockItemRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

}
