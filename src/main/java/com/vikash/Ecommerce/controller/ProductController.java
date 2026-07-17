package com.vikash.Ecommerce.controller;


import com.vikash.Ecommerce.dto.CategoryResponseDTO;
import com.vikash.Ecommerce.dto.PageResponseDTO;
import com.vikash.Ecommerce.dto.ProductResponseDTO;
import com.vikash.Ecommerce.dto.ProductSearchCriteriaDTO;
import com.vikash.Ecommerce.service.CategoryService;
import com.vikash.Ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {


    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PageResponseDTO<ProductResponseDTO>> getAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return ResponseEntity.ok(
                productService.getAllProducts(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));

    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDTO<ProductResponseDTO>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        ProductSearchCriteriaDTO criteria =
                ProductSearchCriteriaDTO.builder()
                        .name(name)
                        .categoryId(categoryId)
                        .minPrice(minPrice)
                        .maxPrice(maxPrice)
                        .inStock(inStock)
                        .page(page)
                        .size(size)
                        .sortBy(sortBy)
                        .direction(direction)
                        .build();

        return ResponseEntity.ok(productService.searchProducts(criteria));
    }
}
