package com.inventory.management.User;

import com.inventory.management.User.dto.AppUserRequest;
import com.inventory.management.User.dto.AppUserResponse;
import com.inventory.management.User.modal.AppUser;
import com.inventory.management.User.repository.AppUserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Validated
public class UserServices {
    private final AppUserRepository appUserRepository;

}
