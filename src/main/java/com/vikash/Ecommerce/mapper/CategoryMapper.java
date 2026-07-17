package com.vikash.Ecommerce.mapper;

import com.vikash.Ecommerce.dto.CategoryRequestDTO;
import com.vikash.Ecommerce.dto.CategoryResponseDTO;
import com.vikash.Ecommerce.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // DTO -> Entity
    public Category toEntity(CategoryRequestDTO dto){

        return Category.builder()
                .name(dto.getName())
                .build();
    }

    // Entity -> DTO
    public CategoryResponseDTO toResponse(Category category){

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    // Update existing entity from DTO
    public void updateEntity(CategoryRequestDTO dto, Category category){
        category.setName(dto.getName());

    }

}
