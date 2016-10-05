package com.bootiful.framework.services;

import com.bootiful.framework.models.User;

public interface IUserService {

    public User findByUsername(String username );

    public void save(User user);

}
