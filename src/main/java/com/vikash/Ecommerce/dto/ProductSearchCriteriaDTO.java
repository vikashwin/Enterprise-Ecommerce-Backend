package com.vikash.Ecommerce.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCriteriaDTO {

    private String name;

    private Long categoryId;

    private Double minPrice;

    private Double maxPrice;

    private Boolean inStock;

    @Builder.Default
    private int page = 0;

    @Builder.Default
    private int size = 10;

    @Builder.Default
    private String sortBy = "id";

    @Builder.Default
    private String direction = "asc";
}