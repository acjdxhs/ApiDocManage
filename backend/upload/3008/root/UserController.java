package com.scut.backend.controller;

import com.scut.backend.model.Result;
import com.scut.backend.model.User;
import com.scut.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理模块
 */
	/**
	*
	* */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

	/**
	*
	* */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result Login(@RequestBody User user) {
        return userService.login(user);
    }

	/**
	*
	* */
    @RequestMapping(value = "signUp", method = RequestMethod.POST)
    public Result SignUp(@RequestBody User user) {
        return userService.signUp(user);
    }
}
