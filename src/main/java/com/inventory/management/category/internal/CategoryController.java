package com.inventory.management.category.internal;

import com.inventory.management.category.categoryServices;
import com.inventory.management.category.dto.UpdateCategoryRequest;
import com.inventory.management.category.modal.Category;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {
    private categoryServices CategoryService;

    @GetMapping
    public List<Category> getAllCategory() {
        return CategoryService.findAllCategory();
    }
    @PostMapping
    public Category addCategory(@RequestBody Category c) {
        return CategoryService.addCategory(c);
    }

    @PutMapping("{id}")
    public Category editCategory(@RequestBody UpdateCategoryRequest c , @PathVariable Long id) {
        return CategoryService.editCategory(id , c);
    }

    @DeleteMapping("{id}")
    public Category deleteCategory(@PathVariable Long id) {
        return CategoryService.deleteCategory(id);
    }
}
