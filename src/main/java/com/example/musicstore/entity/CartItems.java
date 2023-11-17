package com.example.musicstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@NoArgsConstructor
@Entity
@Table(name = "cart_items", schema = "store")
public class CartItems implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private ShoppingCart cart;

    @NotNull
    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Column(name = "amount")
    private double amount;

    public CartItems(Product product, ShoppingCart cart, int quantity, double amount) {
        this.product = product;
        this.cart = cart;
        this.quantity = quantity;
        this.amount = amount;
    }
}
