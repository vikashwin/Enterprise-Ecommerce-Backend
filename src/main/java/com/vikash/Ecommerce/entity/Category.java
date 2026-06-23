package com.vikash.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate  sequence unique id
    @Column(
            name = "category_id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "category_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @ManyToMany(mappedBy = "category" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private List<Product> product;
}
