package com.vikash.Ecommerce.service;

import com.vikash.Ecommerce.dto.*;
import com.vikash.Ecommerce.entity.RefreshToken;
import com.vikash.Ecommerce.entity.Role;
import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.entity.type.UserRole;
import com.vikash.Ecommerce.exception.RoleNotFoundException;
import com.vikash.Ecommerce.exception.UserAlreadyExistsException;
import com.vikash.Ecommerce.mapper.UserMapper;
import com.vikash.Ecommerce.repository.RefreshTokenRepository;
import com.vikash.Ecommerce.repository.RoleRepository;
import com.vikash.Ecommerce.repository.UserRepository;
import com.vikash.Ecommerce.security.CustomUserDetails;
import com.vikash.Ecommerce.security.JwtService;
import com.vikash.Ecommerce.security.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository repository;

    public UserResponseDTO register(UserRequestDTO userRequestDTO){

        if(userRepository.existsByEmail(userRequestDTO.getEmail())){
            throw new UserAlreadyExistsException(
                    "User already exists with email: " + userRequestDTO.getEmail()
            );
        }
        User user = userMapper.toEntity(userRequestDTO);

        Role customerRole = roleRepository.findByName(UserRole.CUSTOMER)
                .orElseThrow(() -> new RoleNotFoundException("Customer role not found"));
        user.getRoles().add(customerRole);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);    //Map userEntity to userResponseDTO and return userResponseDTO

    }

    public AuthResponseDTO login(UserLoginDTO userLoginDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword())
        );
        User user = userRepository.findUserByEmail(userLoginDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        RefreshToken refreshToken = refreshTokenService.create(user);
        String accessToken = jwtService.generateToken(new CustomUserDetails(user));

        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpiration())
                .build();
    }

    public AuthResponseDTO refresh(RefreshTokenRequestDTO request){
        RefreshToken oldToken = refreshTokenService.verify( request.getRefreshToken() );
        User user = oldToken.getUser();
        repository.delete(oldToken);
        RefreshToken newToken = refreshTokenService.create(user);
        String accessToken = jwtService.generateToken(new CustomUserDetails(user));
        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(newToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpiration())
                .build();

    }




}
