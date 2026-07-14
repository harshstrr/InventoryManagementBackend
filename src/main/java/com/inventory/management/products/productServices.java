package com.inventory.management.products;

import com.inventory.management.category.modal.Category;
import com.inventory.management.category.repository.categoryRepo;
import com.inventory.management.products.dto.UpdateProductRequest;
import com.inventory.management.products.modal.Product;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.inventory.management.products.repository.productsRepo;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class productServices {
    private productsRepo ProductsRepo;
    private categoryRepo CategoryRepo;

    public List<Product> findAllProducts() {
        return ProductsRepo.findAll();
    }

    public Product addProduct(Product p) {
        ProductsRepo.save(p);
        return p;
    }

    public Optional<Product> getProduct(Long id) {
        return ProductsRepo.findById(id);
    }

    @Transactional
    public Product editProduct(Long id, UpdateProductRequest request) {
        Product product = ProductsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        Category category = CategoryRepo.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.categoryId()));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setCategory(category);
        product.setIsActive(true);
        product.setUnit(request.unit());
        product.setCostPrice(request.costPrice());
        product.setSellingPrice(request.sellingPrice());
        product.setReorderThreshold(request.reorderThreshold());
        product.setReorderQty(request.reorderQty());
        // updatedAt is handled automatically by @PreUpdate — no need to set it here

        return ProductsRepo.save(product);
    }

    public Product deleteProduct(Long id) {
        Product product = ProductsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setIsActive(false);

        return ProductsRepo.save(product);

    }
}
