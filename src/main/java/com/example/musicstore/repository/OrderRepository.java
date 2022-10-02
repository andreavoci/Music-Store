package com.example.musicstore.repository;

import com.example.musicstore.entity.Order;
import com.example.musicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByPurchaseTime(LocalDate date);

    @Query("select o from Order o where o.purchaseTime > ?1 and o.purchaseTime < ?2 and o.user = ?3")
    List<Order> findByUserInPeriod(Date startDate, Date endDate, User user);
}
