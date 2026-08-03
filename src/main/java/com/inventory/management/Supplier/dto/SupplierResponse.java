package com.inventory.management.Supplier.dto;

import com.inventory.management.Supplier.modal.Supplier;

public record SupplierResponse (
        Long id, String name, String contactPerson ,
        String phone , String email, String address,
        String gstNo,Boolean isActive
) {
    public static SupplierResponse from(Supplier s) {
        return new SupplierResponse(s.getId() , s.getName() , s.getContactPerson() , s.getPhone() , s.getEmail() , s.getAddress() , s.getGstNo() , s.getIsActive() );
    }
}
