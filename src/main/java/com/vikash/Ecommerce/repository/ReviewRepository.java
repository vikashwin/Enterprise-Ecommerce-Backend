package com.vikash.Ecommerce.repository;

import com.vikash.Ecommerce.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review , Long> {
}
