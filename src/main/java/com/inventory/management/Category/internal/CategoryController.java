package com.inventory.management.Category.internal;

import com.inventory.management.Common.ApiResponse;
import com.inventory.management.Category.categoryServices;
import com.inventory.management.Category.dto.UpdateCategoryRequest;
import com.inventory.management.Category.modal.Category;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor

public class CategoryController {
    private categoryServices CategoryService;

    @GetMapping("/getAllCategory")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategory() {
        try {
            return ResponseEntity.ok(ApiResponse.success(CategoryService.findAllCategory() , "Successfully Fetched Categories"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }


    @PostMapping("/add-category")
    public ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody Category c) {
        try{
            return ResponseEntity.ok(ApiResponse.success(CategoryService.addCategory(c) , "Successfully Added Category"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
}

    @PutMapping("/edit-category/{id}")
    public ResponseEntity<ApiResponse<Category>> editCategory(@RequestBody UpdateCategoryRequest c , @PathVariable Long id) {
        try{
            return ResponseEntity.ok(ApiResponse.success( CategoryService.editCategory(id , c) , "Successfully Edited Category" ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<ApiResponse<Category>> deleteCategory(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ApiResponse.success( CategoryService.deleteCategory(id) , "Successfully deleted Category" ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
