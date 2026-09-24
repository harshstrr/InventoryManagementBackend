package com.inventory.management;

import com.inventory.management.Common.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/health")
public class Health {
    @GetMapping("")
    public ResponseEntity<ApiResponse<Void>> getHealth(){
        return ResponseEntity.ok(ApiResponse.success(null , "Server is Live"));
    }
}
