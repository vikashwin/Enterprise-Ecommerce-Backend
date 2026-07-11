package com.vikash.Ecommerce.service;

import com.vikash.Ecommerce.dto.UserRequestDTO;
import com.vikash.Ecommerce.dto.UserResponseDTO;
import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.exception.ResourceNotFoundException;
import com.vikash.Ecommerce.exception.UserAlreadyExistsException;
import com.vikash.Ecommerce.mapper.UserMapper;
import com.vikash.Ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {



    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO){
        User user = userMapper.toEntity(userRequestDTO); //Map userRequestDTO to UserEntity
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(
                    "User with email " + user.getEmail() + " already exists");
        }
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser); //Map userEntity to userResponseDTO and return userResponseDTO
    }

    @Transactional
    public UserResponseDTO updateUserById(UserRequestDTO userRequestDTO , Long id){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with Id : " + id));
        userMapper.updateEntity(userRequestDTO , existingUser);
//        User updatedUser =  userRepository.save(existingUser);  // By using Transaction the line was not necessary
        return userMapper.toResponse(existingUser);
    }

    public UserResponseDTO getUserById(Long id){
        User user =  userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + id
                        ));
        return userMapper.toResponse(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User deleteUserById(Long id){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id : " + id));
        userRepository.deleteById(id);
        return existingUser;
    }

    public String deleteAll(){
        userRepository.deleteAll();
        return "All data Deleted";
    }



}
