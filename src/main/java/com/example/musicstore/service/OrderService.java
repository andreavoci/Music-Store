package com.example.musicstore.service;

import com.example.musicstore.entity.*;
import com.example.musicstore.repository.OrderItemsRepository;
import com.example.musicstore.repository.OrderRepository;
import com.example.musicstore.repository.UserRepository;
import com.example.musicstore.support.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderItemsRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService cartService;

    @Transactional(readOnly = false)
    public Order purchaseOrder(Order order, String username) throws ProductNotFoundException, BadOrderException, OutOfStockException, UserNotFoundException {
        Optional<User> userFromDb = userRepository.findByUsername(username);
        ShoppingCart cart;
        double totalAmount = 0;
        if(userFromDb.isPresent()){
            cart = cartService.getCartByUser(userFromDb.get());
            if(cart!=null){
                for(CartItems cartItems : cart.getCartItems()){
                    if(cartItems.getQuantity()>cartItems.getProduct().getStock())
                        throw new OutOfStockException("Product out of stock");
                    totalAmount += cartItems.getProduct().getPrice() * cartItems.getQuantity();
                }
                if(totalAmount != order.getAmount())
                    throw new BadOrderException("Bad order");
            }else{
                throw new BadOrderException("Cart inexistent");
            }
        }else{
            throw new UserNotFoundException("User not found");
        }
        Set<OrderItems> orderItemsFromDb = new HashSet<>();
        Order orderFromDb = new Order(userFromDb.get(), orderItemsFromDb, order.getEmail(), order.getTelephone(), order.getShippingAddress(), totalAmount, order.getStatus(), order.getPaymentMethod() );
        for(CartItems cartItems : cart.getCartItems()){
            Product p = cartItems.getProduct();
            productService.updateProduct(p.setStock(p.getStock()- cartItems.getQuantity()));
            OrderItems updatedOrderItems = new OrderItems(cartItems.getProduct(), orderFromDb, cartItems.getQuantity(), cartItems.getAmount());
            orderItemRepository.save(updatedOrderItems);
            orderItemsFromDb.add(updatedOrderItems);
        }
        Order orderFinal = orderRepository.save(orderFromDb);
        ShoppingCart cartFromDb = this.cartService.getCartByUser(userFromDb.get());
        cartService.clearCart(cartFromDb);
        return orderFinal;
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(String username) throws UserNotFoundException {
        Optional<User> userFromDb = userService.getUserByUsername(username);
        if(userFromDb.isEmpty()) return null;
        return orderRepository.findByUser(userFromDb.get());
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserInPeriod(User user, Date startDate, Date endDate) throws UserNotFoundException, DateWrongRangeException {
        if( !userRepository.existsById(user.getId())) throw new UserNotFoundException("User not found");
        if( startDate.compareTo(endDate)>=0) throw new DateWrongRangeException();
        return orderRepository.findByUserInPeriod(startDate,endDate,user);
    }

    @Transactional(readOnly = true)
    public Order getOrderById(long id){
        Optional<Order> orderFromDb = orderRepository.findById(id);
        if(orderFromDb.isEmpty()) return null;
        return orderFromDb.get();
    }

    @Transactional(readOnly = false)
    public void deleteOrderById(int id){
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
}
