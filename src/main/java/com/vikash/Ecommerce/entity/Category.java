package com.vikash.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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

    @Builder.Default
    @ManyToMany(mappedBy = "categories",
            fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();
}
