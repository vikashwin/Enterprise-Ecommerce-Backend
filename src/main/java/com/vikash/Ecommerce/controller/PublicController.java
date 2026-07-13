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
@RequestMapping("/public")
public class PublicController {


    private final UserService userService; //Recommended by Spring because of no field injection and immutable


    @GetMapping("/test")
    public String test(){
        return "Test is Working";
    }

}

