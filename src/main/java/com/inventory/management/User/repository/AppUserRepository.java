package com.inventory.management.User.repository;

import com.inventory.management.User.dto.AppUserResponse;
import com.inventory.management.User.modal.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser , Long> {
    Optional<AppUser> findByMobileNumber(BigInteger mobileNumber);
}
