package com.inventory.management.Warehouse.repository;

import com.inventory.management.Warehouse.modal.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse , Long> {
}
