package com.github.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/login1")
public class Login1Controller {


    @GetMapping(value = "/toLogin")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @PostMapping(value = "fail")
    public String fail() {
        return "login fail";
    }
}
