package com.inventory.management.Supplier.dto;

import com.inventory.management.Supplier.modal.Supplier;

public record SupplierSummary (
        Long id,
        String name
){
    public static SupplierSummary from(Supplier s) {
        if(s == null) {
            return null;
        }
        return new SupplierSummary(s.getId() , s.getName());
    }
}
