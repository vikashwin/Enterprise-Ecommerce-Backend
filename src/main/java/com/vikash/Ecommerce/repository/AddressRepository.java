package com.vikash.Ecommerce.repository;

import com.vikash.Ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository< Address ,Long > {
}
