package com.example.musicstore.controller;

import com.example.musicstore.entity.User;
import com.example.musicstore.service.UserService;
import com.example.musicstore.support.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("(hasRole('CUSTOMER') and #username == principal.username) or hasRole('ADMIN')")
    @GetMapping(value = "/{username}", produces = "application/json")
    public ResponseEntity<?> getUser(@PathVariable("username") String username){
        Optional<User> optionalUser = userService.getUserByUsername(username);
        return (optionalUser.isPresent())
                ? ResponseEntity.ok(optionalUser.get())
                : ResponseEntity.badRequest().body(new ResponseMessage("Nonexistent User"));
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }
}
