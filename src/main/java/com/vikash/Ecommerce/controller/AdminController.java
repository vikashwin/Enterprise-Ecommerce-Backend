package com.vikash.Ecommerce.controller;

import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;

    @GetMapping
    public ResponseEntity< List<User> >getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll(){
        userService.deleteAll();
        return ResponseEntity.ok("All Users Deleted");
    }
}
