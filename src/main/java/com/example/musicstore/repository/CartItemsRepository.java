package com.example.musicstore.repository;

import com.example.musicstore.entity.CartItems;
import com.example.musicstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

}
