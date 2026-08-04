package com.inventory.management.Stocks;

import com.inventory.management.PurchaseOrder.dto.CreatedByResponse;
import com.inventory.management.Stocks.modal.*;
import com.inventory.management.Products.modal.Product;
import com.inventory.management.Stocks.repository.StockItemRepository;
import com.inventory.management.Stocks.repository.StockMovementRepository;
import com.inventory.management.User.modal.AppUser;
import com.inventory.management.User.repository.AppUserRepository;
import com.inventory.management.Warehouse.modal.Warehouse;

import lombok.AllArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StockServices {

    private final StockItemRepository stockItemRepository;
    private final StockMovementRepository stockMovementRepository;
    private final AppUserRepository appUserRepository;


    @Transactional
    public void recordPurchase(Product product, Warehouse warehouse, int qty, Long referenceId , AppUser createdby) {
        StockItem item = stockItemRepository
                .findByProductIdAndWarehouseId(product.getId(), warehouse.getId())
                .orElseGet(() -> createStockItem(product, warehouse));

        item.setQuantity(item.getQuantity() + qty);       // "the balance" goes up
        stockItemRepository.save(item);

        saveMovement(product, warehouse, MovementType.PURCHASE_IN, qty, "PURCHASE_ORDER", referenceId , createdby);
        // ^ "the transaction log" gets one new line
    }

   @Transactional
    public void recordSale(Product product, Warehouse warehouse, int qty, Long referenceId, AppUser createdby) {
        StockItem item = stockItemRepository
                .findByProductIdAndWarehouseId(product.getId(), warehouse.getId())
                .orElseThrow(() -> new RuntimeException(
                        "No stock exists for " + product.getSku() + " at this warehouse"));

        if (item.getQuantity() < qty) {
            throw new RuntimeException(
                    "Only " + item.getQuantity() + " units available for " + product.getSku());
        }

        item.setQuantity(item.getQuantity() - qty);       // "the balance" goes down
        stockItemRepository.save(item);
        saveMovement(product, warehouse, MovementType.SALE_OUT, qty, "SALES_ORDER", referenceId, createdby);
    }

    private StockItem createStockItem(Product product, Warehouse warehouse) {
        StockItem item = new StockItem();
        item.setProduct(product);
        item.setWarehouse(warehouse);
        item.setQuantity(0);
        return item;
    }

    private void saveMovement(Product product, Warehouse warehouse, MovementType type,
                              int qty, String referenceType, Long referenceId , AppUser createdby) {

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setWarehouse(warehouse);
        movement.setType(type);
        movement.setQuantity(qty);
        movement.setReferenceType(referenceType);
        movement.setReferenceId(referenceId);
        movement.setCreatedBy(createdby);
        stockMovementRepository.save(movement);
    }

    @Transactional
    public void recordReturn(Product product, Warehouse warehouse, int qty, Long referenceId, AppUser createdBy) {
        StockItem item = stockItemRepository
                .findByProductIdAndWarehouseId(product.getId(), warehouse.getId())
                .orElseThrow(() -> new RuntimeException("No stock record to return into"));

        item.setQuantity(item.getQuantity() + qty);   // ← stock goes UP, opposite of recordSale
        stockItemRepository.save(item);

        saveMovement(product, warehouse, MovementType.RETURN, qty, "SALES_ORDER", referenceId, createdBy);
    }
}