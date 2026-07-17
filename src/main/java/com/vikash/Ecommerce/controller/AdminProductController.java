package com.vikash.Ecommerce.controller;

import com.vikash.Ecommerce.dto.*;
import com.vikash.Ecommerce.service.CategoryService;
import com.vikash.Ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {


    private final ProductService productService;


    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid
            @RequestBody ProductRequestDTO productRequestDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(productRequestDTO));

    }

//    @GetMapping
//    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
//        return ResponseEntity.ok(productService.getAllProducts());
//
//    }

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

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @Valid
            @RequestBody ProductRequestDTO productRequestDTO,
            @PathVariable Long id
    ){
        return ResponseEntity.ok(productService.updateProduct(productRequestDTO , id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");

    }

}
