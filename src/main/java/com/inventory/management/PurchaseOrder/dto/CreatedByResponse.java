package com.inventory.management.PurchaseOrder.dto;

import com.inventory.management.User.modal.AppUser;

import java.math.BigInteger;

public record CreatedByResponse(
        Long id,
        String username,
        BigInteger mobileNumber
) {
    public static CreatedByResponse from(AppUser user) {
        if(user == null) {
            return null;
        }
        return new CreatedByResponse(user.getId() , user.getUsername() , user.getMobileNumber());
    }
}
