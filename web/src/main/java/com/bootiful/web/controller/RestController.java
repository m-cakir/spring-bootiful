package com.bootiful.web.controller;

import com.bootiful.framework.annotation.Logging;
import com.bootiful.framework.repository.projection.UserResource;
import com.bootiful.framework.service.UserService;
import com.bootiful.web.spec.UserSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    UserService userService;

    @Logging(method = "users")
    public Page<UserResource> users(UserSpec spec, @PageableDefault Pageable pageable) {
        return userService.findAll(spec, UserResource.class, pageable);
    }

}
