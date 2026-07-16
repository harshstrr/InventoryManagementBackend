package com.inventory.management.Products.repository;

import com.inventory.management.Products.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productsRepo extends JpaRepository<Product, Long> {

}
