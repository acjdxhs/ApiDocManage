package com.scut.backend.web.controller;

import com.scut.backend.web.model.User;
import com.scut.backend.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String Login(@RequestBody User user) {
        return userService.login(user);
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String SignUp(@RequestBody User user) {
        return userService.signUp(user);
    }
}
