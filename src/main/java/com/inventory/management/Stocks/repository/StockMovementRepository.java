package com.inventory.management.Stocks.repository;

import com.inventory.management.Stocks.modal.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
//    Page<StockMovement> findByProductIdAndWarehouseId(Long productId, Long warehouseId, Pageable pageable);
//    Page<StockMovement> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

}
