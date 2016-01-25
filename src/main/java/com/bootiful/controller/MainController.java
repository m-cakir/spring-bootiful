package com.bootiful.controller;

import org.springframework.stereotype.Controller;

@Controller
public class MainController extends BaseController {

    public String login(){

        return render("login");
    }


    public String index(){

        return render("index");
    }
}
