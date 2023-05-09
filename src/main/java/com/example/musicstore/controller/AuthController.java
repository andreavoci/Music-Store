package com.example.musicstore.controller;

import com.example.musicstore.service.SignupRequest;
import com.example.musicstore.service.UserService;
import com.example.musicstore.support.ResponseMessage;
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
    UserService userService;


    @PostMapping(value = "/signin", consumes = {"application/json"})
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignupRequest signupRequest){
        try{
            return ResponseEntity.ok(userService.authenticateUser(signupRequest));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseMessage("Incorrect credentials"));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/signup/admin", consumes = {"application/json"})
    public ResponseEntity<ResponseMessage> registerAdmin(@Valid @RequestBody SignupRequest signupRequest){
        if(!signupRequest.getRole().contains("admin"))
            return ResponseEntity.badRequest().body(new ResponseMessage("Access Denied"));
        return getMessageResponseResponseEntity(signupRequest);
    }

    @PostMapping(value = "/signup/customer", consumes = {"application/json"})
    public ResponseEntity<ResponseMessage> registerCustomer(@Valid @RequestBody SignupRequest signupRequest){
        System.out.println("Ciao");
        if(signupRequest.getRole()!=null && signupRequest.getRole().contains("admin"))
            return ResponseEntity.badRequest().body(new ResponseMessage("Access Denied"));
        return getMessageResponseResponseEntity(signupRequest);
    }

    @NotNull
    private ResponseEntity<ResponseMessage> getMessageResponseResponseEntity(@Valid @RequestBody SignupRequest signupRequest) {
        if(userService.checkUsername(signupRequest.getUsername()))
            return ResponseEntity.badRequest().body(new ResponseMessage("Username unavailable"));
        if(userService.checkEmail(signupRequest.getEmail()))
            return ResponseEntity.badRequest().body(new ResponseMessage("Email unavailable"));
        System.out.println("ciao2");
        userService.registerUser(signupRequest);
        return ResponseEntity.ok(new ResponseMessage("The user has been successfully registered"));
    }
}
