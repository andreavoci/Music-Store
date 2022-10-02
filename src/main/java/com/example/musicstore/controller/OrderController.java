package com.example.musicstore.controller;

import com.example.musicstore.entity.Order;
import com.example.musicstore.entity.User;
import com.example.musicstore.service.OrderService;
import com.example.musicstore.support.ResponseMessage;
import com.example.musicstore.support.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody @Valid Order order){
        try{
            return new ResponseEntity<>(orderService.purchaseOrder(order, order.getUser().getUsername()), HttpStatus.OK);
        } catch (ProductNotFoundException | BadOrderException |
                 OutOfStockException | UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product quantity unavailable!", e);
        }
    }

    @GetMapping("/{user}")
    public List<Order> getOrders(@RequestBody @Valid @PathVariable("user") User user) throws UserNotFoundException {
        return orderService.getOrdersByUser(user.getUsername());
    }

    @GetMapping("/{user}/{startDate}/{endDate}")
    public ResponseEntity<?> getOrdersInPeriod(@Valid @PathVariable("user") User user, @PathVariable("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date start, @PathVariable("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date end) {
        try {
            List<Order> result = orderService.getOrdersByUserInPeriod(user, start, end);
            if ( result.size() <= 0 ) {
                return new ResponseEntity<>(new ResponseMessage("No results!"), HttpStatus.OK);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!", e);
        } catch (DateWrongRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date must be previous end date!", e);
        }
    }
}
