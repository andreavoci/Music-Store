package com.example.musicstore.controller;

import com.example.musicstore.entity.Order;
import com.example.musicstore.entity.Request;
import com.example.musicstore.entity.User;
import com.example.musicstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public List<Order> getAll(@RequestBody Request<String> body){return orderService.getAll(body);}
}
