package com.inventory.management.Stocks.repository;

import com.inventory.management.Stocks.modal.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, Long> {
    Optional<StockItem> findByProductIdAndWarehouseId(Long productId , Long warehouseId);
    List<StockItem> findByProductId(Long productId);
    List<StockItem> findByWarehouseId(Long warehouseId);

    @Query("SELECT s FROM StockItem s WHERE s.quantity <= s.product.reorderThreshold")
    List<StockItem> findLowStockItems();
}
