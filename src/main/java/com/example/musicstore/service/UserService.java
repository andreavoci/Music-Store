package com.example.musicstore.service;

import com.example.musicstore.entity.User;
import com.example.musicstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUser(Long id){
        return userRepository.findById(id).get();
    }

    public ResponseEntity create(User user) {
        Optional<User> userByEmail = userRepository.findUserByEmail(user.getEmail());
        if(userByEmail.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email taken");
        }
        else{
            User u = new User(user.getUsername(), user.getEmail(), encoder.encode(user.getPassword()));
            return ResponseEntity.ok(userRepository.save(user));
        }
    }
}
