package com.vikash.Ecommerce.service;

import com.vikash.Ecommerce.dto.PageResponseDTO;
import com.vikash.Ecommerce.dto.UserResponseDTO;
import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.exception.ResourceNotFoundException;
import com.vikash.Ecommerce.mapper.PageMapper;
import com.vikash.Ecommerce.mapper.UserMapper;
import com.vikash.Ecommerce.repository.RefreshTokenRepository;
import com.vikash.Ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PageMapper pageMapper;

//    public List<UserResponseDTO> getAllUsers() {
//        return userRepository.findAll()
//                .stream()
//                .map(userMapper::toResponse)
//                .toList();
//    }

    @Transactional
    public PageResponseDTO<UserResponseDTO> getAllUsers(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> userPage = userRepository.findAll(pageable);
        List<UserResponseDTO> users = userPage.getContent()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        return pageMapper.toPageResponse(userPage, users);
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