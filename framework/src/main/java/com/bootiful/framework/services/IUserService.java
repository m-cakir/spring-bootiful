package com.bootiful.framework.services;

import com.bootiful.framework.models.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();

    User findById(long id );

    User findByUsername(String username );

    void save(User user);

}
