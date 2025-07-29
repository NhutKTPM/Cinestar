package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.UserEntity;
import com.da3.MovieTicket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers (){
        return userRepository.findAll();
    }

    public void createUser(UserEntity user){
        userRepository.save(user);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
    }

    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
