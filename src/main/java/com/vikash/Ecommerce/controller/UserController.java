package com.vikash.Ecommerce.controller;


import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User existingUser = userService.getUserById(id);
        return new ResponseEntity<>(existingUser , HttpStatus.OK);
//        return ResponseEntity.ok(userService.getUserById(id));  This is one liner code

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@Valid @RequestBody User user , @PathVariable Long id){
        return ResponseEntity.ok(userService.updateUserById(user , id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);

        return ResponseEntity.ok("User Deleted Successfully");
    }
}
