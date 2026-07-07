package com.vikash.Ecommerce.controller;

import com.vikash.Ecommerce.dto.UserLoginDTO;
import com.vikash.Ecommerce.dto.UserRequestDTO;
import com.vikash.Ecommerce.dto.UserResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/register")
    public void register(@RequestBody UserRequestDTO userRequestDTO){

    }

    @PostMapping("/login")
    public void login(@RequestBody UserLoginDTO userLoginDTO){

    }

    @GetMapping("/logout")
    public void logout(){

    }





}
