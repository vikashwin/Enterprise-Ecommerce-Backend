package com.vikash.Ecommerce.controller;


import com.vikash.Ecommerce.dto.CartItemRequestDTO;
import com.vikash.Ecommerce.dto.CartResponseDTO;
import com.vikash.Ecommerce.security.CustomUserDetails;
import com.vikash.Ecommerce.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponseDTO> getCart(
            @AuthenticationPrincipal CustomUserDetails user
    ){

        return ResponseEntity.ok(cartService.getMyCart(user.getId()));

    }


    @PostMapping("/items")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartResponseDTO> addItem(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody CartItemRequestDTO dto
    ){


        return ResponseEntity.ok(cartService.addItem(
                        dto,
                        user.getId()
                )
        );

    }




    @DeleteMapping("/items/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> removeItem(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user
    ){

        cartService.removeItem(id, user.getId());
        return ResponseEntity.ok("Item removed from cart");

    }

}
