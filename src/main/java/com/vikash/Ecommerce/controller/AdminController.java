package com.vikash.Ecommerce.controller;

import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAllUser();
    }

    @DeleteMapping
    public String deleteAll(){
        return userService.deleteAll();
    }
}
