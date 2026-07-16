package com.inventory.management.Category.repository;


import com.inventory.management.Category.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface categoryRepo extends JpaRepository<Category, Long> {

}
