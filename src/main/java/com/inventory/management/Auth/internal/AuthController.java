package com.inventory.management.Auth.internal;

import com.inventory.management.Auth.dto.AuthResponse;
import com.inventory.management.Auth.dto.RefreshRequest;
import com.inventory.management.Common.ApiResponse;
import com.inventory.management.Auth.AuthServices;
import com.inventory.management.Auth.dto.LoginRequest;
import com.inventory.management.Auth.dto.LoginResponse;
import com.inventory.management.User.dto.AppUserRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthServices authServices;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest payload) {
        try{
            return ResponseEntity.ok(ApiResponse.success( LoginResponse.from(authServices.loginWithPassword(payload)) , "Successfully Login "));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody @Valid RefreshRequest req ) {
        try {
            return ResponseEntity.status(201).body(ApiResponse.success(authServices.refreshToken(req) , "Successfully generated access token"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<Void>> sendOtp(@RequestParam BigInteger mobileNumber) {
        try{
            authServices.sendOtp(mobileNumber);
            return ResponseEntity.ok(ApiResponse.success(null , "Successfully Send OTP"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<LoginResponse>> verifyOtp(@RequestParam("mobileNumber") BigInteger mobileNumber , @RequestParam("otp") Long otp) {
        try{
            return ResponseEntity.ok(ApiResponse.success(authServices.verifyOtp(mobileNumber , otp) , "Successfully Verify Otp"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/new-password")
    public ResponseEntity<ApiResponse<LoginResponse>> newPassword(@RequestBody() LoginRequest payload) {
        try{
            authServices.createNewPassword(payload.mobileNumber() , payload.password());
            return ResponseEntity.ok(ApiResponse.success(null, "Successfully Created New Password"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<ApiResponse<LoginResponse>> createUser (@RequestBody() AppUserRequest user) {
        try {
            return ResponseEntity.ok(ApiResponse.success(authServices.createNewUser(user) , "Successfully Sign-Up Completed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/disable-user")
    public ResponseEntity<ApiResponse<Void>> DeleteUser (@RequestBody() LoginRequest payload) {
        try {
            authServices.disableUser(payload);
            return ResponseEntity.ok(ApiResponse.success(null, "Successfully Disabled Profile"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
