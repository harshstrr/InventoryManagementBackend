package com.inventory.management.Auth;

import com.inventory.management.User.modal.AppUser;

public record AuthResult(AppUser user, String accessToken) {}
