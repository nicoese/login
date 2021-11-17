package com.login.login.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Configuration
@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

//    @PostMapping("login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("caca")
    public String getIndex(){
        return "index";
    }
}
