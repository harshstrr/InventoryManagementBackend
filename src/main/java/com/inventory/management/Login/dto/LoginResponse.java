package com.inventory.management.Login.dto;

import com.inventory.management.User.modal.AppUser;

public record LoginResponse(
        Long id,
        String username,
        Boolean isRegister
) {
    public static LoginResponse from(AppUser user) {
        if(user == null){
            return null;
        }
        return new LoginResponse(user.getId() , user.getUsername() , user.getIsRegister());
    }
}
