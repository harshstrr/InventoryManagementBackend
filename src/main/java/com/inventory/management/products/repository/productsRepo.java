package com.inventory.management.products.repository;

import com.inventory.management.products.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface productsRepo extends JpaRepository<Product, Long> {

}
