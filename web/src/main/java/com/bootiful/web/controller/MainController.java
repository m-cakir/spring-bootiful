package com.bootiful.web.controller;

import com.bootiful.framework.models.User;
import com.bootiful.framework.services.IUserService;
import com.bootiful.web.dto.UserDTO;
import com.bootiful.web.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Controller
public class MainController extends BaseController {

    @Autowired
    UserValidator userValidator;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    IUserService userService;

    public MainController(){
        setViewBase("page");
    }

    public String login(){

        return render("login");
    }

    public String index(){

        return render("index");
    }

    public String signup(UserDTO dto, BindingResult result){

        userValidator.validate(dto, result);

        if (result.hasErrors()){

            for (FieldError fer : result.getFieldErrors()){

                System.out.println(fer.getField() + " > " + fer.getCode() + " > " + fer.getDefaultMessage());

            }

            System.out.println("signup has errors...");

        } else {

            User user = new User();
            user.setUsername(dto.getUsername());
            user.setPassword(encoder.encode(dto.getPassword()));
            user.setEnabled(true);

            userService.save(user);
        }

        return redirectTo(null);
    }
}
