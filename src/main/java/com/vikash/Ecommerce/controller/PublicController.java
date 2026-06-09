package com.vikash.Ecommerce.controller;

import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String test(){
        return "Test is Working";
    }

    @PostMapping
    public User saveUser(@RequestBody  User user){
        return userService.saveUser(user);
    }
}

