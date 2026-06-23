package com.vikash.Ecommerce.entity;

import com.vikash.Ecommerce.entity.type.UserGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //Make object creation Cleaner
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_name_dob", columnNames = {"user_name", "date_of_birth"})
        },     //It can create a constraints where name+dob are always unique
        indexes = {
        @Index(name = "idx_user_dob", columnList = "date_of_birth")
        }      // It is creating a new table that help to find user by age rapidly
        )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Generate  sequence unique id
    @Column(
            name = "user_id",
            updatable = false
    )
    private Long id;


    @Column(
            name = "user_name",
            nullable = false
    )
    @NotNull
    private String name;

    @Column(
            name = "user_password",
            nullable = false
    )
    @NotNull
    private String password;

    @Column(
            name = "user_email",
            nullable = false,
            unique = true
    )
    @Email
    private String email;

    @Column(
            name = "user_number",
            nullable = false,
            unique = true,
            length = 10
    )
    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNumber;

    @Column(name = "date_of_birth", nullable = false)
    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "gender" , nullable = false )
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    //@OneToMany relationship always exit in inverse side
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , orphanRemoval = true)
    @NotNull
    private List<Address> addresses;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy="user", cascade = CascadeType.PERSIST)
    private List<Order> orders;

    @OneToMany(mappedBy="user" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Review> reviews;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp //Create data and time automatically
    @Column(
            name = "created_at",
            updatable = false,
            nullable = false
    )
    private LocalDateTime createdAt;


    @Builder.Default
    private Boolean enabled = true;

    @Builder.Default
    private Boolean emailVerified = false;

    @Builder.Default
    private Boolean accountLocked = false;

    @Builder.Default
    private Boolean deleted = false;
}
