package com.vikash.Ecommerce.controller;


import com.vikash.Ecommerce.dto.AddressRequestDTO;
import com.vikash.Ecommerce.dto.AddressResponseDTO;
import com.vikash.Ecommerce.security.CustomUserDetails;
import com.vikash.Ecommerce.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {


    private final AddressService addressService;



    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AddressResponseDTO> createAddress(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody AddressRequestDTO dto
    ){

        return ResponseEntity.ok(addressService.createAddress(dto, user.getId()));

    }




    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AddressResponseDTO>> getMyAddresses(
            @AuthenticationPrincipal CustomUserDetails user
    ){

        return ResponseEntity.ok(addressService.getMyAddresses(user.getId()));

    }





    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody AddressRequestDTO dto
    ){

        return ResponseEntity.ok(
                addressService.updateAddress(
                        id,
                        dto,
                        user.getId()
                )
        );

    }





    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user
    ){

        addressService.deleteAddress(id, user.getId());
        return ResponseEntity.ok(
                "Address deleted successfully"
        );

    }

}
