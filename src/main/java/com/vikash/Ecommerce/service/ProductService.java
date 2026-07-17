package com.vikash.Ecommerce.service;

import com.vikash.Ecommerce.dto.PageResponseDTO;
import com.vikash.Ecommerce.dto.ProductRequestDTO;
import com.vikash.Ecommerce.dto.ProductResponseDTO;
import com.vikash.Ecommerce.dto.ProductSearchCriteriaDTO;
import com.vikash.Ecommerce.entity.Category;
import com.vikash.Ecommerce.entity.Product;
import com.vikash.Ecommerce.exception.ResourceNotFoundException;
import com.vikash.Ecommerce.mapper.PageMapper;
import com.vikash.Ecommerce.mapper.ProductMapper;
import com.vikash.Ecommerce.repository.CategoryRepository;
import com.vikash.Ecommerce.repository.ProductRepository;
import com.vikash.Ecommerce.specification.ProductSpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final PageMapper pageMapper;

    @Transactional
    public ProductResponseDTO createProduct(@Valid ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        Set<Category> categories = new HashSet<>(
                categoryRepository.findAllById(dto.getCategoryIds())
        );

        if (categories.size() != dto.getCategoryIds().size()) {
            throw new ResourceNotFoundException("One or more categories not found.");
        }

        product.setCategories(categories);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }


//    public List<ProductResponseDTO> getAllProducts() {
//        return productRepository.findAll()
//                .stream()
//                .map(productMapper::toResponse)
//                .toList();
//    }

    @Transactional
    public PageResponseDTO<ProductResponseDTO> getAllProducts(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductResponseDTO> products = productPage.getContent()
                .stream()
                .map(productMapper::toResponse)
                .toList();
        return pageMapper.toPageResponse(productPage, products);
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id : " + id));

        return productMapper.toResponse(product);
    }

    @Transactional
    public ProductResponseDTO updateProduct(@Valid ProductRequestDTO dto, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id : " + id));

        productMapper.updateEntity(dto, product);
        Set<Category> categories = new HashSet<>(
                categoryRepository.findAllById(dto.getCategoryIds())
        );

        if (categories.size() != dto.getCategoryIds().size()) {
            throw new ResourceNotFoundException("One or more categories not found.");
        }

        product.setCategories(categories);
        Product updated = productRepository.save(product);
        return productMapper.toResponse(updated);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id : " + id));

        productRepository.delete(product);
    }

    public PageResponseDTO<ProductResponseDTO> searchProducts(ProductSearchCriteriaDTO criteria) {

        Sort sort = criteria.getDirection().equalsIgnoreCase("desc")
                ? Sort.by(criteria.getSortBy()).descending()
                : Sort.by(criteria.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(
                criteria.getPage(),
                criteria.getSize(),
                sort
        );
        Specification<Product> specification = Specification.unrestricted();

        if (criteria.getName() != null && !criteria.getName().isBlank()) {
            specification = specification.and(
                    ProductSpecification.hasName(criteria.getName())
            );
        }
        if (criteria.getCategoryId() != null) {
            specification = specification.and(
                    ProductSpecification.hasCategory(criteria.getCategoryId())
            );
        }
        if (criteria.getMinPrice() != null) {
            specification = specification.and(
                    ProductSpecification.minPrice(criteria.getMinPrice())
            );
        }
        if (criteria.getMaxPrice() != null) {
            specification = specification.and(
                    ProductSpecification.maxPrice(criteria.getMaxPrice())
            );
        }
        if (Boolean.TRUE.equals(criteria.getInStock())) {
            specification = specification.and(
                    ProductSpecification.inStock()
            );
        }
        Page<Product> page = productRepository.findAll(specification, pageable);
        List<ProductResponseDTO> products =
                page.getContent()
                        .stream()
                        .map(productMapper::toResponse)
                        .toList();

        return pageMapper.toPageResponse(page, products);
    }
}