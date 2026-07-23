package com.vikash.Ecommerce.controller;

import com.vikash.Ecommerce.dto.PageResponseDTO;
import com.vikash.Ecommerce.dto.UserResponseDTO;
import com.vikash.Ecommerce.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminControllers {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin Dashboard";
    }

//    @GetMapping("/users")
//    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
//        return ResponseEntity.ok(adminService.getAllUsers());
//    }

    @GetMapping("/users")
    public ResponseEntity<PageResponseDTO<UserResponseDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        return ResponseEntity.ok(
                adminService.getAllUsers(page, size, sortBy, direction)
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAllUsers() {
        adminService.deleteAllUsers();
        return ResponseEntity.ok("All users deleted.");
    }

}