package com.vikash.Ecommerce.repository;

import com.vikash.Ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product> {

}
