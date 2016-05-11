package com.bootiful.core.service;

import com.bootiful.core.model.User;

public interface IUserService {

    public User findByUsername( String username );

    public void save(User user);

}
