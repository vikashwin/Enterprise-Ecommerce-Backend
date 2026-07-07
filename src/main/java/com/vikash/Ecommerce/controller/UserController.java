package com.vikash.Ecommerce.controller;


import com.vikash.Ecommerce.dto.UserRequestDTO;
import com.vikash.Ecommerce.dto.UserResponseDTO;
import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {



    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        UserResponseDTO existingUser = userService.getUserById(id);
        return new ResponseEntity<>(existingUser , HttpStatus.OK);
//        return ResponseEntity.ok(userService.getUserById(id));  This is one liner code

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@Valid @RequestBody UserRequestDTO userRequestDTO , @PathVariable Long id){
        return ResponseEntity.ok(userService.updateUserById(userRequestDTO , id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);

        return ResponseEntity.ok("User Deleted Successfully");
    }
}
