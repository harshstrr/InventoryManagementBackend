package com.inventory.management.category;

import com.inventory.management.category.dto.UpdateCategoryRequest;
import com.inventory.management.category.modal.Category;
import com.inventory.management.category.repository.categoryRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class categoryServices {
    private categoryRepo CategoryRepo;

    public Category addCategory ( Category c) {
        System.out.println(c);
         CategoryRepo.save(c);
         return c;
    }

    public List<Category> findAllCategory() {
        return CategoryRepo.findAll();
    }

    public Category editCategory(Long id, UpdateCategoryRequest c) {
        Category item = CategoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        item.setName(c.name());
        item.setParent_id(c.parent_id());
        item.setIsActive(true);

        return CategoryRepo.save(item);
    }

    public Category deleteCategory(Long id) {
        Category item =  CategoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        item.setIsActive(false);
        return CategoryRepo.save(item);

    }
}
