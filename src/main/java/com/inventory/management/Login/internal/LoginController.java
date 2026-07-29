package com.inventory.management.Login.internal;

import com.inventory.management.Common.ApiResponse;
import com.inventory.management.Login.LoginServices;
import com.inventory.management.Login.dto.LoginRequest;
import com.inventory.management.Login.dto.LoginResponse;
import com.inventory.management.User.dto.AppUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private LoginServices loginServices;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest payload) {
        try{
            return ResponseEntity.ok(ApiResponse.success(loginServices.loginWithPassword(payload) , "Successfully Login "));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<Void>> sendOtp(@RequestParam BigInteger mobileNumber) {
        try{
            loginServices.sendOtp(mobileNumber);
            return ResponseEntity.ok(ApiResponse.success(null , "Successfully Send OTP"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<LoginResponse>> verifyOtp(@RequestParam("mobileNumber") BigInteger mobileNumber , @RequestParam("otp") Long otp) {
        try{
            return ResponseEntity.ok(ApiResponse.success(loginServices.verifyOtp(mobileNumber , otp) , "Successfully Verify Otp"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/new-password")
    public ResponseEntity<ApiResponse<LoginResponse>> newPassword(@RequestBody() LoginRequest payload) {
        try{
            loginServices.createNewPassword(payload.mobileNumber() , payload.password());
            return ResponseEntity.ok(ApiResponse.success(null, "Successfully Created New Password"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<ApiResponse<LoginResponse>> createUser (@RequestBody() AppUserRequest user) {
        try {
            return ResponseEntity.ok(ApiResponse.success(loginServices.createNewUser(user) , "Successfully Sign-Up Completed"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/disable-user")
    public ResponseEntity<ApiResponse<Void>> DeleteUser (@RequestBody() LoginRequest payload) {
        try {
            loginServices.disableUser(payload);
            return ResponseEntity.ok(ApiResponse.success(null, "Successfully Disabled Profile"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
