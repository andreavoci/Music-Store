package com.example.musicstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "cart_items", schema = "store")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private ShoppingCart cart;

    @NotNull
    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Column(name = "amount")
    private double amount;

    public CartItems(Product product, ShoppingCart cart, int quantity, double amount){
        this.product=product;
        this.cart=cart;
        this.quantity=quantity;
        this.amount=amount;
    }
}
