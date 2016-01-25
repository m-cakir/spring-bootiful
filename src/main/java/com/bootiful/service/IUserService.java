package com.bootiful.service;

import com.bootiful.model.User;

public interface IUserService {

    public User findByUsername( String username );

    public void save(User user);

}
