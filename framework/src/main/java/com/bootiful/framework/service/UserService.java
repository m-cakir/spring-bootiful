package com.bootiful.framework.service;

import com.bootiful.framework.domain.User;
import com.bootiful.framework.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, Long, UserRepository> {

}
