package com.vikash.Ecommerce.repository;

import com.vikash.Ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
