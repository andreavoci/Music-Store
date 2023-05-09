package com.example.musicstore.entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "store", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Username is missing!")
    @Size(max = 50)
    private String username;

    @NotBlank(message = "Email is missing!")
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank(message = "Password is missing!")
    @Size(max = 120)
    private String password;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", schema = "store",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
    private ShoppingCart shoppingCart;

    public User(String username, String email, String password){
        this.username=username;
        this.email=email;
        this.password=password;
        this.registrationDate=LocalDate.now();
    }

}
