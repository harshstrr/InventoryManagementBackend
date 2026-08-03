package com.inventory.management.PurchaseOrder.repository;

import com.inventory.management.PurchaseOrder.modal.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
