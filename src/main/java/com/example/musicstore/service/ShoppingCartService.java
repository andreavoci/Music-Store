package com.example.musicstore.service;

import com.example.musicstore.entity.CartItems;
import com.example.musicstore.entity.Product;
import com.example.musicstore.entity.ShoppingCart;
import com.example.musicstore.entity.User;
import com.example.musicstore.repository.CartItemsRepository;
import com.example.musicstore.repository.ProductRepository;
import com.example.musicstore.repository.ShoppingCartRepository;
import com.example.musicstore.repository.UserRepository;
import com.example.musicstore.support.exception.ProductNotFoundException;
import com.example.musicstore.support.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = false)
    public ShoppingCart updateCart(CartItems cartItems, User user) throws ProductNotFoundException{
        Optional<Product> productFromDb = productRepository.findById(cartItems.getProduct().getId());
        Optional<ShoppingCart> cartFromDb = shoppingCartRepository.findByUser(user);
        if(productFromDb.isEmpty())
            throw new ProductNotFoundException("Product not found!");
        else{
            Product p = productFromDb.get();
            cartItems.setCart(cartFromDb.get());
            cartItems.setAmount(p.getPrice()* cartItems.getQuantity());
            Set<CartItems> products = cartFromDb.get().getCartItems();
            double newAmount = cartFromDb.get().getAmount();
            for(CartItems cI : products){
                if(cI.getProduct().equals(p)){
                    newAmount -= p.getPrice()* cI.getQuantity();
                    products.remove(cI);
                    cartItemsRepository.delete(cI);
                    cartFromDb.get().setAmount(newAmount);
                    return shoppingCartRepository.save(cartFromDb.get());
                } else if (cartItems.getQuantity() < p.getStock()) {
                    newAmount += p.getPrice()*(cartItems.getQuantity() - cI.getQuantity());
                    cI.setAmount(p.getPrice()* cartItems.getQuantity());
                    cI.setQuantity(cartItems.getQuantity());
                    cartFromDb.get().setAmount(newAmount);
                    return shoppingCartRepository.save(cartFromDb.get());
                }else{
                    throw new ProductNotFoundException("Product not available in the requested quantity!");
                }
            }
            newAmount += p.getPrice()* cartItems.getQuantity();
            products.add(cartItems);
            cartItemsRepository.save(cartItems);
            cartFromDb.get().setAmount(newAmount);
            return shoppingCartRepository.save(cartFromDb.get());
        }
    }

    @Transactional(readOnly = true)
    public Optional<ShoppingCart> getCartByUser(User user) throws UserNotFoundException {
        if(!userRepository.existsById(user.getId())) throw new UserNotFoundException("User not found!");
        return shoppingCartRepository.findByUser(user);
    }

    public void clearCart(ShoppingCart shoppingCart){
        shoppingCart.setAmount(0);
        shoppingCart.setCartItems(null);
    }
}
