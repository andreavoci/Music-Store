package com.example.musicstore.service;

import com.example.musicstore.entity.Request;
import com.example.musicstore.entity.User;
import com.example.musicstore.repository.UserRepository;
import com.example.musicstore.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public ResponseEntity<String> register(String username, String email, String password){
        if(!email.contains("@"))
            return ResponseEntity.badRequest().body("Email not valid!");
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        if(! userByEmail.isPresent()){
            User user = new User(username, email, encoder.encode(password));
            userRepository.save(user);
            String token = Util.generateToken();
            return new ResponseEntity<String>("{\"token\":\""+token+"\", \"id\":\""+user.getId()+"\" }", HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Email already used!",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> login(String username, String password) {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        System.out.println(userByUsername);
        if(userByUsername.isPresent()){
            System.out.println(userByUsername.get().getPassword());
            System.out.println(password);
            System.out.println(userByUsername.get().getPassword().equals(password));
            if(encoder.matches(password, userByUsername.get().getPassword())){
                String newtoken = Util.generateToken();
                userByUsername.get().setToken(newtoken);
                return new ResponseEntity<String>("{\"token\":\""+newtoken+"\", \"id\":\""+userByUsername.get().getId()+"\" }",HttpStatus.OK);

            }
            else{
                return new ResponseEntity<String>("Password uncorrected",HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<String>("Username uncorrected",HttpStatus.BAD_REQUEST);
        }
    }

    public Optional<User> authenticate(Request<?> authBody){
        return userRepository.findById(authBody.getId());
    }

}
