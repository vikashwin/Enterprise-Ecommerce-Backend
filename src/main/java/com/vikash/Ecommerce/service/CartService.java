package com.vikash.Ecommerce.service;


import com.vikash.Ecommerce.dto.CartItemRequestDTO;
import com.vikash.Ecommerce.dto.CartResponseDTO;
import com.vikash.Ecommerce.entity.*;
import com.vikash.Ecommerce.exception.ResourceNotFoundException;
import com.vikash.Ecommerce.mapper.CartMapper;
import com.vikash.Ecommerce.repository.CartRepository;
import com.vikash.Ecommerce.repository.ProductRepository;
import com.vikash.Ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class CartService {


    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;



    // GET CART
    public CartResponseDTO getMyCart(Long userId){
        Cart cart = cartRepository.findByUserId(userId)
                        .orElseGet(() -> createCart(userId));

        return cartMapper.toResponse(cart);

    }


    // ADD PRODUCT TO CART
    @Transactional
    public CartResponseDTO addItem(
            CartItemRequestDTO dto,
            Long userId
    ){
        Cart cart = cartRepository.findByUserId(userId)
                        .orElseGet(() -> createCart(userId));

        Product product = productRepository.findById(dto.getProductId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"
                                ));

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setCart(cart);
        cart.getItems().add(item);

        return cartMapper.toResponse(cartRepository.save(cart));

    }


    // REMOVE ITEM
    @Transactional
    public void removeItem(Long itemId, Long userId){

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart not found")
                );

        CartItem item = cart.getItems()
                .stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart item not found"));

        cart.getItems().remove(item);

        cartRepository.save(cart);
    }


    private Cart createCart(Long userId){
        User user = userRepository.findById(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));
        Cart cart = Cart.builder()
                        .user(user)
                        .build();
        return cartRepository.save(cart);

    }


}
