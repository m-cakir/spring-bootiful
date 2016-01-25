package com.bootiful.service;

import com.bootiful.model.User;
import com.bootiful.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByUsername (String username){

        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {

        if(user != null) {
            userRepository.save(user);
        }

    }
}
