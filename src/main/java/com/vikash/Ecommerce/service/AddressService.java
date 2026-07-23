package com.vikash.Ecommerce.service;


import com.vikash.Ecommerce.dto.AddressRequestDTO;
import com.vikash.Ecommerce.dto.AddressResponseDTO;
import com.vikash.Ecommerce.entity.Address;
import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.exception.ResourceNotFoundException;
import com.vikash.Ecommerce.mapper.AddressMapper;
import com.vikash.Ecommerce.repository.AddressRepository;
import com.vikash.Ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AddressService {


    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;


    // CREATE ADDRESS
    @Transactional
    public AddressResponseDTO createAddress(AddressRequestDTO dto, Long userId){

        User user = userRepository.findById(userId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        Address address = addressMapper.toEntity(dto);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.toResponse(savedAddress);
    }



    // GET ALL LOGGED USER ADDRESSES
    public List<AddressResponseDTO> getMyAddresses(Long userId){

        User user = userRepository.findById(userId).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));
        return user.getAddresses()
                .stream()
                .map(addressMapper::toResponse)
                .toList();

    }

    // UPDATE ADDRESS
    @Transactional
    public AddressResponseDTO updateAddress(
            Long addressId,
            AddressRequestDTO dto,
            Long userId
    ) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found")
                );

        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("You cannot update this address");
        }

        addressMapper.updateEntity(dto, address);

        return addressMapper.toResponse(address);
    }





    // DELETE ADDRESS

    @Transactional
    public void deleteAddress(Long addressId, Long userId){

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address not found"
                        ));

        if(!address.getUser().getId().equals(userId)){
            throw new RuntimeException(
                    "You cannot delete this address"
            );

        }
        addressRepository.delete(address);

    }


}
