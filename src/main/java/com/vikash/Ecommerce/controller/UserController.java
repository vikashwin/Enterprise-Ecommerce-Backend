package com.vikash.Ecommerce.controller;


import com.vikash.Ecommerce.dto.UserRequestDTO;
import com.vikash.Ecommerce.dto.UserResponseDTO;
import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.security.CustomUserDetails;
import com.vikash.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {



    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDTO> me(@AuthenticationPrincipal CustomUserDetails user){
        return ResponseEntity.ok(
                userService.getMyProfile(user.getId())
        );
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDTO> updateMyProfile(@AuthenticationPrincipal CustomUserDetails currentUser,
                                  @Valid @RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(
                userService.updateMyProfile(userRequestDTO, currentUser.getId())
                );

    }

    @DeleteMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteMyAccount(@AuthenticationPrincipal CustomUserDetails user){
       userService.deleteMyAccount(user.getId());
       return ResponseEntity.ok("Account deleted successfully.");


    }


}
