package com.inventory.management.category.repository;


import com.inventory.management.category.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface categoryRepo extends JpaRepository<Category, Long> {

}
