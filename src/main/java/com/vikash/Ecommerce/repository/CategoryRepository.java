package com.vikash.Ecommerce.repository;

import com.vikash.Ecommerce.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Long> {


    boolean existsByNameIgnoreCase(String name);
}
