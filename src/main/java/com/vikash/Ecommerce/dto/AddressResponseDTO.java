package com.vikash.Ecommerce.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponseDTO {

    private Long id;

    private String street;

    private String city;

    private String state;

    private String country;

    private String zipCode;
}
