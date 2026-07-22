package com.inventory.management.Warehouse;

import com.inventory.management.Warehouse.modal.Warehouse;
import com.inventory.management.Warehouse.repository.WarehouseRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class WarehouseServices {
    private WarehouseRepository warehouseRepository;

    public List<Warehouse> getAllWarehouse () {
        return warehouseRepository.findAll();
    }

    public Warehouse addWarehouse(@Valid Warehouse w) {
        return warehouseRepository.save(w);
    }

    public Warehouse deleteWarehouse(Long id) {
        Warehouse w = warehouseRepository.findById(id).orElseThrow();
        w.setIsActive(false);
        return warehouseRepository.save(w);
    }
}
