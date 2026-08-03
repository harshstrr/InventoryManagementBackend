package com.inventory.management.Products.internal;



import com.inventory.management.Common.ApiResponse;
import com.inventory.management.Products.dto.AddProductRequest;
import com.inventory.management.Products.dto.ProductResponse;
import com.inventory.management.Products.dto.UpdateProductRequest;
import com.inventory.management.Products.modal.Product;
import com.inventory.management.Products.productServices;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    @Autowired
    private productServices ProductServices;

    @GetMapping("/getAllProducts")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts (){
        try {
            List<ProductResponse> response = ProductServices.findAllProducts()
                    .stream()
                    .map(ProductResponse::from)
                    .toList();
            return ResponseEntity.ok(ApiResponse.success( response , "Successfully fetch Products"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }

    }

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse<ProductResponse>> addProduct(@RequestBody AddProductRequest entity) {
        try {
            return ResponseEntity.ok(ApiResponse.success( ProductResponse.from(ProductServices.addProduct(entity)) , "Product Added Successfully" ));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<Product>>> getProduct(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(ApiResponse.success( ProductServices.getProduct(id) , "Successfully fetched Product."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/edit-product/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> editProduct(@PathVariable Long id , @RequestBody UpdateProductRequest p) {
        try {
            return ResponseEntity.ok(ApiResponse.success(ProductResponse.from(ProductServices.editProduct(id , p)) , "Successfully Edited Product" ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    @DeleteMapping("/delete-Product/{id}")
    public ResponseEntity<ApiResponse<Product>> deleteProduct (@PathVariable Long id){
        try {
            return ResponseEntity.ok(ApiResponse.success(  ProductServices.deleteProduct(id) , "Successfully Edited Product" ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
