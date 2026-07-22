package com.inventory.management.Login;

import com.inventory.management.Login.dto.LoginRequest;
import com.inventory.management.Login.dto.LoginResponse;
import com.inventory.management.User.modal.AppUser;
import com.inventory.management.User.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@AllArgsConstructor
@Slf4j
public class LoginServices {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;


    public LoginResponse loginWithPassword(LoginRequest payload) {
        AppUser user = appUserRepository.findByMobileNumber(payload.mobileNumber());

        if(passwordEncoder.matches(payload.password() , user.getPassword())){
            user.setIsRegister(true);
        } else {
            throw new RuntimeException("Invalid Password");
        }

        return LoginResponse.from(user);

    }

    public void createNewPassword(BigInteger mobileNumber , String password){
        AppUser user = appUserRepository.findByMobileNumber(mobileNumber);
        if(user.getIsRegister()) {
            user.setPassword(passwordEncoder.encode(password));
            ResponseEntity.ok("New Password Created Successfully");
        } else {
            throw new RuntimeException();
        }
        appUserRepository.saveAndFlush(user);
    }

    public void sendOtp (BigInteger mobileNumber) {
        AppUser user = appUserRepository.findByMobileNumber(mobileNumber);

        user.setOtp(123456L);
        appUserRepository.saveAndFlush(user);
    }

    public LoginResponse verifyOtp(BigInteger mobileNumber , Long otp) {
        AppUser user = appUserRepository.findByMobileNumber(mobileNumber);
        if(user.getOtp().equals(otp)){
            user.setIsRegister(true);
        } else {
            throw new RuntimeException("Invalid OTP");
        }
        appUserRepository.saveAndFlush(user);
        return LoginResponse.from(user);
    }



}
