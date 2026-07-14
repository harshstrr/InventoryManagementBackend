package com.inventory.management.products.internal;



import com.inventory.management.products.dto.UpdateProductRequest;
import com.inventory.management.products.modal.Product;
import com.inventory.management.products.productServices;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private productServices ProductServices;

    @GetMapping
    public List<Product> getAllProducts (){
        return ProductServices.findAllProducts();

    }

    @PostMapping
    public Product addProduct(@RequestBody Product entity) {
        return ProductServices.addProduct(entity);
    }

    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable Long id) {
        return ProductServices.getProduct(id);
    }

    @PutMapping("/{id}")
    public Product editProduct(@PathVariable Long id , @RequestBody UpdateProductRequest p) {
        return ProductServices.editProduct(id , p);
    }
    @DeleteMapping("/{id}")
    public Product deleteProduct (@PathVariable Long id){
        return ProductServices.deleteProduct(id);
    }
}
