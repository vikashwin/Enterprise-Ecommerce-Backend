package com.vikash.Ecommerce.controller;

import com.vikash.Ecommerce.dto.CategoryResponseDTO;
import com.vikash.Ecommerce.dto.PageResponseDTO;
import com.vikash.Ecommerce.service.CategoryService;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/categories")
@RequiredArgsConstructor
@RestController
public class CategoryController {


    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PageResponseDTO<CategoryResponseDTO>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        return ResponseEntity.ok(
                categoryService.getAllCategories(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoriesById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));

    }
}
