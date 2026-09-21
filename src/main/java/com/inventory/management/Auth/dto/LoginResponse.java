package com.inventory.management.Auth.dto;

import com.inventory.management.User.modal.AppUser;

public record LoginResponse(
        Long id,
        String name,
        String accessToken,
        String refreshToken
) {
    public static LoginResponse from(AppUser user , String accessToken) {
        if(user == null){
            return null;
        }
        return new LoginResponse(user.getId() , user.getFirstName() , accessToken , user.getRefreshToken());
    }
}
