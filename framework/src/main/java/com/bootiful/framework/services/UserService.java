package com.bootiful.framework.services;

import com.bootiful.framework.models.User;
import com.bootiful.framework.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {

        return userRepository.findAll();
    }

    public User findByUsername (String username){

        return userRepository.findByUsername(username);
    }

    public void save(User user) {

        if(user != null) {
            userRepository.save(user);
        }

    }
}
