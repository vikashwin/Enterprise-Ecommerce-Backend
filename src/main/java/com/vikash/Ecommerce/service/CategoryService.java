package com.vikash.Ecommerce.service;

import com.vikash.Ecommerce.dto.CategoryRequestDTO;
import com.vikash.Ecommerce.dto.CategoryResponseDTO;
import com.vikash.Ecommerce.dto.PageResponseDTO;
import com.vikash.Ecommerce.entity.Category;
import com.vikash.Ecommerce.exception.CategoryAlreadyExistsException;
import com.vikash.Ecommerce.exception.ResourceNotFoundException;
import com.vikash.Ecommerce.mapper.CategoryMapper;
import com.vikash.Ecommerce.mapper.PageMapper;
import com.vikash.Ecommerce.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final PageMapper pageMapper;


    @CacheEvict(value = "categories", allEntries = true)
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
        if (categoryRepository.existsByNameIgnoreCase(requestDTO.getName())) {
            throw new CategoryAlreadyExistsException(
                    "Category already exists with name : " + requestDTO.getName()
            );
        }
        Category category = categoryMapper.toEntity(requestDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

//    @Transactional
//    public List<CategoryResponseDTO> getAllCategories() {
//        return categoryRepository.findAll()
//                .stream()
//                .map(categoryMapper::toResponse)
//                .toList();
//    }

    @Cacheable(
            value = "categories",
            key = "'page-' + #page + '-size-' + #size + '-sort-' + #sortBy + '-dir-' + #direction"
    )
    @Transactional
    public PageResponseDTO<CategoryResponseDTO> getAllCategories(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<CategoryResponseDTO> categories = categoryPage.getContent()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();

        return pageMapper.toPageResponse(categoryPage, categories);
    }

    @Cacheable(value = "categories", key = "#id")
    @Transactional
    public CategoryResponseDTO getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id : " + id));

        return categoryMapper.toResponse(category);
    }

    @CacheEvict(value = "categories", allEntries = true)
    public CategoryResponseDTO updateCategory(CategoryRequestDTO requestDTO, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id : " + id));

        if (!category.getName().equalsIgnoreCase(requestDTO.getName())
                && categoryRepository.existsByNameIgnoreCase(requestDTO.getName())) {

            throw new CategoryAlreadyExistsException(
                    "Category already exists with name : " + requestDTO.getName());
        }
        categoryMapper.updateEntity(requestDTO, category);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(updatedCategory);
    }

    @CacheEvict(value = "categories", allEntries = true)
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id : " + id));

        categoryRepository.delete(category);
    }
}