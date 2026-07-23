package com.vikash.Ecommerce.repository;

import com.vikash.Ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository< Address ,Long > {

    List<Address> findByUserId(Long userId);
}
