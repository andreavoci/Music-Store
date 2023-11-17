package com.example.musicstore.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order", schema = "store")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

    @NotNull
    @OneToMany(cascade =CascadeType.ALL, mappedBy = "order")
    private List<OrderItems> products = new ArrayList<>();

    private double amount;

    private Date purchaseTime;

    public Order(User user, List<OrderItems> orderItems, double amount){
        this.user=user;
        this.products=orderItems;
        this.amount=amount;
        this.purchaseTime= new Date();
    }
}
