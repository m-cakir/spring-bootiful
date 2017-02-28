package com.bootiful.framework.services;

import com.bootiful.framework.models.User;
import com.bootiful.framework.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Cacheable(value = "users")
    public List<User> findAll() {

        try {

            Thread.sleep(5000);

        } catch (Exception e){
            throw new RuntimeException(e);
        }

        return userRepository.findAll();
    }

    @Cacheable(value = "user")
    public User findById (long id){

        return userRepository.findById(id);
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
