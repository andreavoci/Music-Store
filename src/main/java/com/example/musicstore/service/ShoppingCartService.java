package com.example.musicstore.service;

import com.example.musicstore.entity.*;
import com.example.musicstore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity getCart(Request<?> authBody) {
        Optional<User> optUser = authService.authenticate(authBody);
        if(optUser.isPresent()){
            Optional<ShoppingCart> optCarrello = shoppingCartRepository.findCartByUserID(optUser.get());
            if(optCarrello.isPresent()){
                return ResponseEntity.ok(optCarrello.get());
            }
            else{
                return ResponseEntity.ok(Optional.empty());
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user doesn't exist");
        }
    }

    public ResponseEntity<?> addToCart(Request<?> body) {
        Object o = body.getBody();
        if(o instanceof CartItems){

            CartItems item = (CartItems) o;
            ResponseEntity response = getCart(body);

            if(response.getStatusCode().is2xxSuccessful()){
                if(response.getBody() instanceof ShoppingCart){
                    ShoppingCart cart = (ShoppingCart) response.getBody();
                    Optional<CartItems> existingItem = cart.getCartItems().stream()
                            .filter(existing -> existing.getProduct().getId() ==(item.getProduct().getId()))
                            .findFirst();
                    if (existingItem.isPresent()) {
                        existingItem.get().setQuantity(existingItem.get().getQuantity() + item.getQuantity());
                    } else {
                        item.setCart(cart);
                        cart.getCartItems().add(item);
                    }

                    shoppingCartRepository.save(cart);
                    return ResponseEntity.ok(Collections.singletonMap("message","update correctly"));
                }
                else{
                    ShoppingCart newCart = new ShoppingCart(authService.authenticate(body).get(),new ArrayList<>(),0);

                    item.setCart(newCart);
                    newCart.getCartItems().add(item);
                    try {
                        shoppingCartRepository.save(newCart);
                        return ResponseEntity.ok(Collections.singletonMap("message", "added correctly"));
                    }catch(OptimisticLockingFailureException e){
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Concurrent modification detected. Please try again.");
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("malformed");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("malformed");
        }
    }

    public ResponseEntity removeItem(Request<?> body){
        Optional<User> optUser = authService.authenticate(body);
        Object o = body.getBody();

        if(optUser.isPresent()) {
            Optional<ShoppingCart> optCarrello = shoppingCartRepository.findCartByUserID(optUser.get());
            if (optCarrello.isPresent()) {
                if (o instanceof Long) {
                    long itemID = (long) o;
                    Optional<CartItems> item = cartItemsRepository.findById(itemID);
                    if (optCarrello.get().getCartItems().contains(item.get())) {
                        optCarrello.get().getCartItems().remove(item.get());
                        cartItemsRepository.delete(item.get());
                        try {
                            shoppingCartRepository.save(optCarrello.get());
                            return ResponseEntity.ok(Collections.singletonMap("message", "deleted correctly"));
                        }catch(OptimisticLockingFailureException e){
                            return ResponseEntity.status(HttpStatus.CONFLICT).body("Concurrent modification detected. Please try again.");
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cart doesn't contain element");
                    }
                }
                else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("malformed element");
                }
            }
            else{
                return ResponseEntity.ok(Collections.singletonMap("message","no cart"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user doesn't exist");
    }

    public ResponseEntity purchase(Request body){
        Optional<User> optUser = authService.authenticate(body);
        if(optUser.isPresent()) {
            Optional<ShoppingCart> optCarrello = shoppingCartRepository.findCartByUserID(optUser.get());
            if (optCarrello.isPresent()) {
                Order newOrder = new Order(optUser.get(),new ArrayList<>(),optCarrello.get().getAmount());
                double totale=0.0;
                if(!optCarrello.get().getCartItems().isEmpty()){
                    for(CartItems c : optCarrello.get().getCartItems()){
                        Product p = c.getProduct();
                        if(p.getStock()<c.getQuantity())
                            return ResponseEntity.badRequest().body("Quantity over stock!");
                        p.setStock(p.getStock()-c.getQuantity());
                        productRepository.save(p);
                    }
                    optCarrello.get().getCartItems().forEach(e->{
                        OrderItems item = new OrderItems(e.getProduct(),e.getAmount());
                        item.setOrder(newOrder);
                        newOrder.getProducts().add(item);
                    });
                    for(OrderItems oi: newOrder.getProducts()){
                        totale+=oi.getAmount();
                    }
                    newOrder.setAmount(totale);
                    orderRepository.save(newOrder);
                    try {
                        shoppingCartRepository.delete(optCarrello.get());
                        return ResponseEntity.ok(Collections.singletonMap("message", "order correctly"));
                    }catch(OptimisticLockingFailureException e){
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Concurrent modification detected. Please try again.");
                    }
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cart is empty");

            }
            else{
                return ResponseEntity.ok(Collections.singletonMap("message","no cart"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user doesn't exist");
    }

    public ResponseEntity<?> update(Request body) {
        Optional<User> optUser = authService.authenticate(body);
        Object o = body.getBody();
        System.out.println(o);

        if (optUser.isPresent()) {
            Optional<ShoppingCart> optCart = shoppingCartRepository.findCartByUserID(optUser.get());

            if (optCart.isPresent()) {
                if (o instanceof CartItems) {
                    CartItems item = (CartItems) o;
                    ShoppingCart cart = optCart.get();
                    Optional<CartItems> existingItem = cart.getCartItems().stream()
                            .filter(existing -> existing.getProduct().getId() == item.getProduct().getId())
                            .findFirst();

                    if (existingItem.isPresent()) {
                        existingItem.ifPresent(cartItems -> {
                            int newQuantity = item.getQuantity();
                            double newAmount = item.getAmount();
                            cartItems.setQuantity(newQuantity);
                            cartItems.setAmount(newAmount);
                        });

                        // Aggiorna l'importo totale del carrello prima del salvataggio
                        updateCartTotal(cart);

                        // Salva il carrello
                        shoppingCartRepository.save(cart);

                        return ResponseEntity.ok(Collections.singletonMap("message", "Cart updated successfully"));
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in the cart");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shopping cart not found");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User doesn't exist");
    }

    private void updateCartTotal(ShoppingCart cart) {
        double totalAmount = cart.getCartItems().stream()
                .mapToDouble(item -> item.getAmount())
                .sum();
        cart.setAmount(totalAmount);
    }
}
