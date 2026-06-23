package com.vikash.Ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate  sequence unique id
    @Column(
            name = "product_id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "product_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @NotNull
    private Double price;

    private Integer stock;

    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> category;

    @OneToMany(mappedBy="product" , cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy="product" , cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

}
