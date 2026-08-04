package com.inventory.management.Auth;

import com.inventory.management.Auth.dto.AuthResponse;
import com.inventory.management.Auth.dto.LoginRequest;
import com.inventory.management.Auth.dto.LoginResponse;
import com.inventory.management.Auth.dto.RefreshRequest;
import com.inventory.management.User.dto.AppUserRequest;
import com.inventory.management.User.modal.AppUser;
import com.inventory.management.User.repository.AppUserRepository;
import com.inventory.management.Utils.jwt.JwtService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServices {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AppUser loginWithPassword(LoginRequest payload) {
        AppUser user = appUserRepository.findByMobileNumber(payload.mobileNumber())
                .orElseThrow (() -> new BadCredentialsException("Invalid mobile number or password"));

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        if (!passwordEncoder.matches(payload.password() , user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        user.setAccessToken(token);
        user.setRefreshToken(refreshToken);
        user.setIsActive(true);

        appUserRepository.saveAndFlush(user);
        return user;
    }

    public AuthResponse refreshToken(RefreshRequest req) {
        if (!jwtService.isValid(req.refreshToken())) {
            throw new RuntimeException("Invalid refresh token");
        }

        Claims claims = jwtService.parseClaims(req.refreshToken());

        if (!"refresh".equals(claims.get("type"))) {
            throw new RuntimeException("Not a refresh token");
        }

        Long userId = Long.valueOf(claims.getSubject());

        AppUser user = appUserRepository.findById(userId)
                .orElseThrow();

        String accessToken = jwtService.generateToken(user);

        return new AuthResponse(accessToken, req.refreshToken());
    }


    public void createNewPassword(BigInteger mobileNumber , String password){
        AppUser user = appUserRepository.findByMobileNumber(mobileNumber)
                .orElseThrow (() -> new BadCredentialsException("Invalid mobile number or password"));

        if(user.getIsRegister()) {
            user.setPassword(passwordEncoder.encode(password));
            ResponseEntity.ok("New Password Created Successfully");
        } else {
            throw new RuntimeException();
        }
        appUserRepository.saveAndFlush(user);
    }

    public void sendOtp (BigInteger mobileNumber) {
        AppUser user = appUserRepository.findByMobileNumber(mobileNumber)
                .orElseThrow (() -> new BadCredentialsException("Invalid mobile number or password"));


        user.setOtp(123456L);
        appUserRepository.saveAndFlush(user);
    }

    public LoginResponse verifyOtp(BigInteger mobileNumber , Long otp) {
        AppUser user = appUserRepository.findByMobileNumber(mobileNumber)
                .orElseThrow (() -> new BadCredentialsException("Invalid mobile number or password"));

        if(user.getOtp().equals(otp)){
            user.setIsActive(true);
            user.setIsRegister(true);
        } else {
            throw new RuntimeException("Invalid OTP");
        }
        appUserRepository.saveAndFlush(user);
        return LoginResponse.from(user);
    }

    public LoginResponse createNewUser(AppUserRequest u ) {
        AppUser user = new AppUser();
        user.setUsername(u.username());
        user.setPassword(passwordEncoder.encode(u.password()));
        user.setEmail(u.email());
        user.setMobileNumber(u.mobileNumber());

        appUserRepository.saveAndFlush(user);

        sendOtp(u.mobileNumber());

        return LoginResponse.from(user);
    }

    public void disableUser (LoginRequest payload) {
        AppUser user = appUserRepository.findByMobileNumber(payload.mobileNumber())
                .orElseThrow (() -> new BadCredentialsException("Invalid mobile number or password"));

        user.setIsActive(false);
        appUserRepository.saveAndFlush(user);
    }

}
