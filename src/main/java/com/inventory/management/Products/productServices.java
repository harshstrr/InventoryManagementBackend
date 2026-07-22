package com.inventory.management.Products;

import com.inventory.management.Category.modal.Category;
import com.inventory.management.Category.repository.categoryRepo;
import com.inventory.management.Products.dto.AddProductRequest;
import com.inventory.management.Products.dto.UpdateProductRequest;
import com.inventory.management.Products.modal.Product;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.inventory.management.Products.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class productServices {
    private ProductRepository ProductRepository;
    private categoryRepo CategoryRepo;

    public List<Product> findAllProducts() {
        return ProductRepository.findAll();
    }

    public Product addProduct(AddProductRequest p) {
        Product product = new Product();

        Category category = CategoryRepo.findById(p.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + p.categoryId()));

        product.setName(p.name());
        product.setSku(p.sku());
        product.setDescription(p.description());
        product.setCategory(category);
        product.setIsActive(true);
        product.setUnit(p.unit());
        product.setCostPrice(p.costPrice());
        product.setSellingPrice(p.sellingPrice());
        product.setReorderThreshold(p.reorderThreshold());
        product.setReorderQty(p.reorderQty());

        return ProductRepository.save(product);
    }

    public Optional<Product> getProduct(Long id) {
        return ProductRepository.findById(id);
    }

    @Transactional
    public Product editProduct(Long id, UpdateProductRequest request) {
        Product product = ProductRepository.findById(id)
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

        return ProductRepository.save(product);
    }

    public Product deleteProduct(Long id) {
        Product product = ProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setIsActive(false);

        return ProductRepository.save(product);

    }
}
