package com.vikash.Ecommerce.controller;

import com.vikash.Ecommerce.dto.CategoryRequestDTO;
import com.vikash.Ecommerce.dto.CategoryResponseDTO;
import com.vikash.Ecommerce.dto.PageResponseDTO;
import com.vikash.Ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RestController
public class AdminCategoryController {

    private final CategoryService categoryService;


    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @Valid
            @RequestBody CategoryRequestDTO categoryRequestDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createCategory(categoryRequestDTO));

    }

//    @GetMapping
//    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
//        return ResponseEntity.ok(categoryService.getAllCategories());
//
//    }

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
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @Valid
            @RequestBody CategoryRequestDTO categoryRequestDTO,
            @PathVariable Long id
    ){
        return ResponseEntity.ok(categoryService.updateCategory(categoryRequestDTO , id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();

    }


}
