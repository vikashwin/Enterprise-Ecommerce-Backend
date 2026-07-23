package com.vikash.Ecommerce.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class CartResponseDTO {


    private Long cartId;

    private List<CartItemResponseDTO> items;

    private Double totalAmount;

}
