package com.vikash.Ecommerce.service;

import com.vikash.Ecommerce.dto.UserResponseDTO;
import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.exception.ResourceNotFoundException;
import com.vikash.Ecommerce.mapper.UserMapper;
import com.vikash.Ecommerce.repository.RefreshTokenRepository;
import com.vikash.Ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RefreshTokenRepository refreshTokenRepository;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
        refreshTokenRepository.deleteByUserId(id);
        userRepository.delete(user);
    }

    @Transactional
    public void deleteAllUsers() {
        refreshTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

}