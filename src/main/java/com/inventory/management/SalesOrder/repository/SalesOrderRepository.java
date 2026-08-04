package com.inventory.management.SalesOrder.repository;

import com.inventory.management.SalesOrder.modal.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder , Long> {
}
