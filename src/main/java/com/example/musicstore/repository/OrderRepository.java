package com.example.musicstore.repository;

import com.example.musicstore.entity.Order;
import com.example.musicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user = ?1")
    List<Order> findOrdersByUserID(User user);
}
