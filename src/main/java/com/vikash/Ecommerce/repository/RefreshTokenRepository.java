package com.vikash.Ecommerce.repository;

import com.vikash.Ecommerce.entity.RefreshToken;
import com.vikash.Ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
        Optional<RefreshToken> findByToken(String token);

        Optional<RefreshToken> findByUser(User user);

        void deleteByUser(User user);
}