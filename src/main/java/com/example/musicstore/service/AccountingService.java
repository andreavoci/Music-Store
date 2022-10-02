package com.example.musicstore.service;

import com.example.musicstore.entity.User;
import com.example.musicstore.repository.UserRepository;
import com.example.musicstore.support.exception.MailUserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountingService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public User registerUser(User user) throws MailUserAlreadyExistsException {
        if(userRepository.existsByEmail(user.getEmail()))
            throw new MailUserAlreadyExistsException("Email already exists!");
        return userRepository.save(user);
    }

    @Transactional(readOnly = false)
    public Optional<User> getUser(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) return Optional.empty();
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){return userRepository.findAll();}
}
