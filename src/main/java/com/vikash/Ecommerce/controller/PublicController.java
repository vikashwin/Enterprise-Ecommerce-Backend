package com.vikash.Ecommerce.controller;

import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @GetMapping("/test")
    public String test(){
        return "Test is Working";
    }

    @PostMapping
    public User saveUser(@Valid @RequestBody  User user){
        return userService.saveUser(user);
    }
}

