package com.vikash.Ecommerce.repository;

import com.vikash.Ecommerce.entity.Role;
import com.vikash.Ecommerce.entity.type.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(UserRole name);

    boolean existsByName(UserRole name);
}
