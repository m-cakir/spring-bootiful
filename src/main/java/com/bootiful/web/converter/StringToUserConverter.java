package com.bootiful.web.converter;

import com.bootiful.core.model.User;
import com.bootiful.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class StringToUserConverter implements Converter<String, User> {

    @Autowired
    UserRepository userRepository;

    @Override
    public User convert(String source) {

        if(source == null || source.isEmpty()){

            return null;
        }

        try {

            return userRepository.findOne(Long.parseLong(source.trim()));

        } catch (Exception e) {

            return null;

        }
    }
}
