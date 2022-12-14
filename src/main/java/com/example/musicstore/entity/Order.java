package com.example.musicstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "order", schema = "store")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @NotNull
    @OneToMany(mappedBy = "order")
    @Column(name = "products")
    private Set<OrderItems> products = new HashSet<>();

    @NotBlank(message = "Email is missing!")
    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 20)
    @Column(name = "telephone")
    private String telephone;

    @NotBlank
    @Column(name = "shipping_address")
    private String shippingAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment")
    private PaymentMethod paymentMethod;

    @Column(name = "total_amount")
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "order_date")
    private LocalDate purchaseTime;

    public Order(User user, Set<OrderItems> orderItems, String email, String telephone, String shippingAddress, double amount, OrderStatus status, PaymentMethod payment){
        this.user=user;
        this.products=orderItems;
        this.email=email;
        this.telephone=telephone;
        this.shippingAddress=shippingAddress;
        this.paymentMethod=payment;
        this.status=status;
        this.amount=amount;
        this.purchaseTime=LocalDate.now();
    }
}
