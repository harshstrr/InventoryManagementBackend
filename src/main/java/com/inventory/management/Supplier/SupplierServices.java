package com.inventory.management.Supplier;

import com.inventory.management.Supplier.dto.CreateSupplierRequest;
import com.inventory.management.Supplier.modal.Supplier;
import com.inventory.management.Supplier.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SupplierServices {
    private final SupplierRepository supplierRepository;

    @Transactional
    public Supplier addSupplier (CreateSupplierRequest s) {
        Supplier supplier = new Supplier();

        supplier.setName(s.name());
        supplier.setAddress(s.address());
        supplier.setContactPerson(s.contactPerson());
        supplier.setEmail(s.email());
        supplier.setPhone(s.phone());
        supplier.setGstNo(s.gstNo());
        supplier.setIsActive(true);

        return supplierRepository.save(supplier);
    }

    public List<Supplier> allSuppliers () {
        return supplierRepository.findAll();
    }
}
