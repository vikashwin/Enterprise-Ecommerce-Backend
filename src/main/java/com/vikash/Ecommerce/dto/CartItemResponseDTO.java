package com.vikash.Ecommerce.dto;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CartItemResponseDTO {


    private Long id;

    private Long productId;

    private String productName;

    private Double price;

    private Integer quantity;

    private Double totalPrice;

}
