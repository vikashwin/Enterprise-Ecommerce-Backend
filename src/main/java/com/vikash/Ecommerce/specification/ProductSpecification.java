package com.vikash.Ecommerce.specification;

import com.vikash.Ecommerce.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasName(String name) {
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) ->
                cb.equal(
                        root.join("categories").get("id"),
                        categoryId
                );
    }

    public static Specification<Product> minPrice(Double price) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> maxPrice(Double price) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> inStock() {
        return (root, query, cb) ->
                cb.greaterThan(root.get("stock"), 0);
    }
}