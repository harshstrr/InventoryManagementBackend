package com.inventory.management.User.internal;

import com.inventory.management.Common.ApiResponse;
import com.inventory.management.Products.dto.UpdateProductRequest;
import com.inventory.management.Products.modal.Product;
import com.inventory.management.User.UserServices;
import com.inventory.management.User.dto.AppUserRequest;
import com.inventory.management.User.dto.AppUserResponse;
import com.inventory.management.User.modal.AppUser;
import com.inventory.management.User.repository.AppUserRepository;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServices userServices;
    private AppUserRepository appUserRepository;


    @GetMapping("/getUser")
    public ResponseEntity<ApiResponse<AppUserResponse>> getUser( @RequestParam(name = "mobileNumber") BigInteger mobileNumber ) {
        try {
            return ResponseEntity.ok(ApiResponse.success(AppUserResponse.from(appUserRepository.findByMobileNumber(mobileNumber)), "Successfully Fetch User"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

}