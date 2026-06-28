package com.vikash.Ecommerce.service;

import com.vikash.Ecommerce.entity.User;
import com.vikash.Ecommerce.exception.ResourceNotFoundException;
import com.vikash.Ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
         userRepository.save(user);
         return user;

    }

    //Use as update user as id
    public User updateUserById(User user , Long id){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id : " + id));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        return userRepository.save(existingUser); // whenever save then they return updated existingUser
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + id
                        ));
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
