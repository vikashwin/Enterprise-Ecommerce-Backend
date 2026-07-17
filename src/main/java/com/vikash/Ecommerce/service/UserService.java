package com.vikash.Ecommerce.service;

import com.vikash.Ecommerce.dto.UserRequestDTO;
import com.vikash.Ecommerce.dto.UserResponseDTO;
import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.exception.ResourceNotFoundException;
import com.vikash.Ecommerce.exception.UserAlreadyExistsException;
import com.vikash.Ecommerce.mapper.UserMapper;
import com.vikash.Ecommerce.repository.RefreshTokenRepository;
import com.vikash.Ecommerce.repository.UserRepository;
import com.vikash.Ecommerce.security.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {



    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RefreshTokenRepository refreshTokenRepository;


    public UserResponseDTO getMyProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponseDTO updateMyProfile(UserRequestDTO userRequestDTO, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
        userMapper.updateEntity(userRequestDTO, user);
        return userMapper.toResponse(user);
    }

    @Transactional
    public void deleteMyAccount(Long id) {
        refreshTokenRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }



}
