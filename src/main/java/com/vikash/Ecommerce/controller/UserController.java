package com.vikash.Ecommerce.controller;


import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.service.UserService;
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
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        User requestedUser = userService.getUserById(id);
        if(requestedUser != null){
            return new ResponseEntity<>(requestedUser , HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Fond" , HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@RequestBody User user , @PathVariable Long id){
        User requestedUser = userService.updateUserById(user, id);
        if(requestedUser != null){
            return new ResponseEntity<>(requestedUser , HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Found" , HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        User requestedUser = userService.deleteUserById(id);
        if(requestedUser != null){
            return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User Not Found" , HttpStatus.NOT_FOUND);


    }
}
