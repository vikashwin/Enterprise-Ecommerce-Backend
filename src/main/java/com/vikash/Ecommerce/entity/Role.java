package com.vikash.Ecommerce.entity;

import com.vikash.Ecommerce.entity.type.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate  sequence unique id
    @Column(
            name = "role_id",
            updatable = false
    )
    private Long id;


    @Column(
            name = "user_role",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private UserRole name;


    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}
