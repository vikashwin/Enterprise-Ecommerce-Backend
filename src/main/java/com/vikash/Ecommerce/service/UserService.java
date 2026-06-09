package com.vikash.Ecommerce.service;

import com.vikash.Ecommerce.entity.User;
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
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null){
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            userRepository.save(existingUser);
            return existingUser;
        }
        return null;
    }

    public User getUserById(Long id){
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null){
            return existingUser;
        }
        return null;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User deleteUserById(Long id){
        User existingUser = userRepository.findById(id).orElse(null);
        if(existingUser != null){
            userRepository.deleteById(id);
            return existingUser;
        }
        return null;
    }

    public String deleteAll(){
        userRepository.deleteAll();
        return "All data Deleted";
    }



}
