package com.vikash.Ecommerce.controller;

import com.vikash.Ecommerce.dto.*;
import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.service.ProductService;
import com.vikash.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {


    private final ProductService productService; //Recommended by Spring because of no field injection and immutable

    @GetMapping("/test")
    public String test(){
        return "Test is Working";
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

