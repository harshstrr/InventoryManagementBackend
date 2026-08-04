package com.inventory.management.PurchaseOrderItem.repository;

import com.inventory.management.SalesOrderItem.modal.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Long> {
}
