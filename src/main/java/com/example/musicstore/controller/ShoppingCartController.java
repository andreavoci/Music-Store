package com.example.musicstore.controller;

import com.example.musicstore.entity.*;
import com.example.musicstore.service.AuthService;
import com.example.musicstore.service.ProductService;
import com.example.musicstore.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;

    @PostMapping(path = "/add")
    public ResponseEntity addToCart(@RequestBody Request<CartItems> body){
        return cartService.addToCart(body);
    }

    @PostMapping(path = "/remove")
    public ResponseEntity<?> remove(@RequestBody Request<Long> body){
        return cartService.removeItem(body);
    }

    @PostMapping(path = "/checkout")
    public ResponseEntity<?> purchase(@RequestBody Request body) {return cartService.purchase(body);}

    @PostMapping(path = "/update")
    public ResponseEntity update(@RequestBody Request<CartItems> body) {return cartService.update(body);}

    @PostMapping
    public ResponseEntity getCart(@RequestBody Request<String> body){
        return cartService.getCart(body);
    }
}
