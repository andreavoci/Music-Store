package com.example.musicstore.service;

import com.example.musicstore.entity.*;
import com.example.musicstore.repository.OrderItemsRepository;
import com.example.musicstore.repository.OrderRepository;
import com.example.musicstore.repository.ShoppingCartRepository;
import com.example.musicstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AuthService authService;

    public List<Order> getAll(Request<?> body){
        Optional<User> optUser = authService.authenticate(body);
        if(optUser.isPresent()) {
            return orderRepository.findOrdersByUserID(optUser.get());
        }
        return new ArrayList<>();
    }
}
