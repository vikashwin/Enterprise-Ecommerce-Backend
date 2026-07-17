package com.vikash.Ecommerce.mapper;

import com.vikash.Ecommerce.dto.CategoryResponseDTO;
import com.vikash.Ecommerce.dto.ProductRequestDTO;
import com.vikash.Ecommerce.dto.ProductResponseDTO;
import com.vikash.Ecommerce.entity.Product;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO dto){
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();

    }

    public ProductResponseDTO toResponse(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .active(product.getActive())
                .createdAt(product.getCreatedAt())
                .categories(
                        product.getCategories()
                                .stream()
                                .map(category -> CategoryResponseDTO.builder()
                                        .id(category.getId())
                                        .name(category.getName())
                                        .build())
                                .collect(Collectors.toSet())
                )
                .build();
    }

    public void updateEntity(ProductRequestDTO dto , Product product){
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
    }


}
