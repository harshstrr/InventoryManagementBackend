package com.inventory.management.Supplier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateSupplierRequest(
   @NotBlank(message = "Name is required")
   String name,

   String contactPerson,

   @Email(message = "Email must be valid")
   String email,

   String phone,
   String address,
   String gstNo
) {}
