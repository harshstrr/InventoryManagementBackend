package com.inventory.management.User.dto;

import com.inventory.management.User.modal.AppUser;

import java.math.BigInteger;
import java.util.List;

public record AppUserResponse(
        Long id,
        String username,
        String email,
        BigInteger mobileNumber,
        Boolean isActive
) {
    public static AppUserResponse from(AppUser u) {
        if(u == null){
            return null;
        }
        return new AppUserResponse(u.getId(), u.getUsername() , u.getEmail() , u.getMobileNumber() , u.getIsActive());
    }

}
