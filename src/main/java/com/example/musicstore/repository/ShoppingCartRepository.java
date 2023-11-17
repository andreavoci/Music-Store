package com.example.musicstore.repository;

import com.example.musicstore.entity.ShoppingCart;
import com.example.musicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT c FROM ShoppingCart c WHERE c.user = ?1")
    Optional<ShoppingCart> findCartByUserID(User user);

    @Query("SELECT c FROM ShoppingCart c WHERE c.id = ?1")
    Optional<ShoppingCart> findCartByID(Long id);
}
