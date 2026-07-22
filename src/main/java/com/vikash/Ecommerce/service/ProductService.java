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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final S3Service s3Service;
    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;


    @CacheEvict(value = "products", allEntries = true)
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

    @Cacheable(
            value = "products",
            key = "'page-' + #page + '-size-' + #size + '-sort-' + #sortBy + '-dir-' + #direction"
    )
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

    @Cacheable(value = "products", key = "#id")
    public ProductResponseDTO getProductById(Long id) {
        System.out.println("Fetching Product From Database...");
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id : " + id));

        return productMapper.toResponse(product);
    }

    @CacheEvict(value = "products", allEntries = true)
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

//    Upload or replace product image
@CacheEvict(value = "products", allEntries = true)
@Transactional
public ProductResponseDTO uploadProductImage(Long productId, MultipartFile image) throws IOException {

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id : " + productId)
            );

    if(!isValidImage(image)){
        throw new RuntimeException(
                "Only JPG, PNG and WEBP images are allowed"
        );
    }
    String oldImageUrl = product.getImageUrl();
    // Upload new image first
    String newImageUrl = s3Service.uploadFile(image);
    // Update database
    product.setImageUrl(newImageUrl);
    Product savedProduct = productRepository.save(product);
    // Delete old image after successful save
    if(oldImageUrl != null){
        s3Service.deleteFile(oldImageUrl);
    }
    return productMapper.toResponse(savedProduct);
}

//    Delete only product image
    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public void deleteProductImage(Long productId){
        Product product = productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Product not found with id : " + productId)
                        );


        if(product.getImageUrl()!=null){
            s3Service.deleteFile(product.getImageUrl());
            product.setImageUrl(null);
            productRepository.save(product);
        }
    }

//    Delete product and S3 image
    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Product not found with id : " + id)
                        );
        // Remove database record
        productRepository.delete(product);
        // Remove image from S3
        if(product.getImageUrl()!=null){
            s3Service.deleteFile(product.getImageUrl());
        }


    }

    private boolean isValidImage(MultipartFile file){
        if(file.isEmpty()){
            return false;
        }

        if(file.getSize() > MAX_IMAGE_SIZE){
            throw new RuntimeException(
                    "Image size must be less than 5MB"
            );
        }
        String contentType = file.getContentType();
        return contentType != null &&
                (
                        contentType.equals("image/jpeg") ||
                                contentType.equals("image/png") ||
                                contentType.equals("image/webp")
                );
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