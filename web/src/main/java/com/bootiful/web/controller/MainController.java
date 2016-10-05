package com.bootiful.web.controller;

import org.springframework.stereotype.Controller;

@Controller
public class MainController extends BaseController {

    public MainController(){
        setViewBase("page");
    }

    public String login(){

        return render("login");
    }


    public String index(){

        return render("index");
    }
}
