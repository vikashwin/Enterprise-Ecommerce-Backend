package com.vikash.Ecommerce.mapper;


import com.vikash.Ecommerce.dto.CartItemResponseDTO;
import com.vikash.Ecommerce.dto.CartResponseDTO;
import com.vikash.Ecommerce.entity.Cart;
import com.vikash.Ecommerce.entity.CartItem;
import org.springframework.stereotype.Component;


import java.util.List;


@Component
public class CartMapper {

    public CartResponseDTO toResponse(Cart cart){
        List<CartItemResponseDTO> items =
                cart.getItems()
                        .stream()
                        .map(this::mapItem)
                        .toList();


        Double total = items.stream()
                        .mapToDouble(CartItemResponseDTO::getTotalPrice)
                        .sum();

        return CartResponseDTO.builder()
                .cartId(cart.getId())
                .items(items)
                .totalAmount(total)
                .build();

    }





    private CartItemResponseDTO mapItem(CartItem item){
        return CartItemResponseDTO.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .totalPrice(
                        item.getProduct().getPrice()
                                *
                                item.getQuantity()
                )
                .build();

    }

}
