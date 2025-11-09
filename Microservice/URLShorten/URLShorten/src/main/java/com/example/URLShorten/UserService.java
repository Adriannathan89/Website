package com.example.URLShorten;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User findURLbyUsername(String Username) {
        return userRepository.findByusername(Username);
    }

    @Transactional
    public void AddUserURL(String username, URL URLData) {
        User currentUser = userRepository.findByusername(username);
        currentUser.addURL(URLData);
        userRepository.save(currentUser);
    }
}