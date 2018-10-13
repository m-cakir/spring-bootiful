package com.bootiful.web.config.security;

import com.bootiful.framework.domain.Role;
import com.bootiful.framework.domain.User;
import com.bootiful.framework.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        logger.debug("Authenticating {}", username);

        String lowercaseUsername = username.toLowerCase();

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + lowercaseUsername + " was not found");
        }

        Role role = user.getRole();
        if (role == null) {
            throw new UsernameNotFoundException("User " + lowercaseUsername + " was not found");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Arrays.asList(authority));
    }
}
