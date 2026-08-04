package com.inventory.management.User.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.math.BigInteger;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
@Builder
public class AppUser {

    private Boolean isRegister = false;
    private Long Otp;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username" , nullable = false)
    private String username;

    @Column(name = "mobile_number", nullable = false)
    @Min(value = 1000000000L, message = "Mobile number must be at least 10 digits")
    @Max(value = 999999999999999L, message = "Mobile number cannot exceed 15 digits")
    private BigInteger mobileNumber; // Kept BigInteger, replaced @Size


    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    public AppUser(AppUser u) {
        this.isActive = u.getIsActive();
        this.createdAt = u.getCreatedAt();
        this.password = u.getPassword();
        this.email = u.getEmail();
        this.mobileNumber = u.getMobileNumber();
        this.username = u.getUsername();
        this.id = u.getId();
        Otp = u.getOtp();
        this.isRegister = u.getIsRegister();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
