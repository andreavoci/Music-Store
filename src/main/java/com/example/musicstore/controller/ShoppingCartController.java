package com.example.musicstore.controller;

import com.example.musicstore.entity.CartItems;
import com.example.musicstore.entity.Product;
import com.example.musicstore.entity.ShoppingCart;
import com.example.musicstore.repository.CartItemsRepository;
import com.example.musicstore.service.AccountingService;
import com.example.musicstore.service.ProductService;
import com.example.musicstore.service.ShoppingCartService;
import com.example.musicstore.support.ResponseMessage;
import com.example.musicstore.support.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    AccountingService accountingService;

    @GetMapping(value = "/add", consumes = {"application/json"})
    public ResponseEntity<?> addToCart(@RequestBody ShoppingCart cart, @RequestParam("cartItems") CartItems cartItems, @RequestParam("quantity") int quantity) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productService.getProducts(cartItems.getProduct().getId());
        if(optionalProduct.isPresent())
            return ResponseEntity.ok(cartService.updateCart(cartItems, cart.getUser()));
        return ResponseEntity.badRequest().body(new ResponseMessage("Bad request"));
    }

    @GetMapping(value = "/remove", consumes = {"application/json"})
    public ResponseEntity<?> remove(@RequestBody ShoppingCart cart, @RequestParam("cartItems") CartItems cartItems, @RequestParam("quantity") int quantity) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productService.getProducts(cartItems.getProduct().getId());
        if(optionalProduct.isPresent()) {
            cartItems.setQuantity(quantity);
            return ResponseEntity.ok(cartService.updateCart(cartItems, cart.getUser()));
        }
        return ResponseEntity.badRequest().body(new ResponseMessage("Bad request"));
    }

    @GetMapping(value = "/products", consumes = {"application/json"})
    public ResponseEntity<?> getProducts(@RequestBody ShoppingCart cart){
        Set<CartItems> products = cart.getCartItems();
        if(!products.isEmpty())
            return ResponseEntity.ok(products);
        return ResponseEntity.badRequest().body(new ResponseMessage("No Products"));
    }
}
