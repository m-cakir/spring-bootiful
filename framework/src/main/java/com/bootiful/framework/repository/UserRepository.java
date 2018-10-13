package com.bootiful.framework.repository;

import com.bootiful.framework.domain.User;

public interface UserRepository extends BaseRepository<User, Long> {

    User findByUsername(String username);

}
