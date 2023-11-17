package com.example.musicstore.controller;

import com.example.musicstore.service.AuthService;
import com.example.musicstore.service.SignupRequest;
import com.example.musicstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody SignupRequest body){return authService.login(body.getUsername(), body.getPassword());}

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody SignupRequest body){return authService.register(body.getUsername(), body.getEmail(), body.getPassword());}

}
