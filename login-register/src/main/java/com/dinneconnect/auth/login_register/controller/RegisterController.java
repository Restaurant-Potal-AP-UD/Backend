package com.dinneconnect.auth.login_register.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinneconnect.auth.login_register.DTO.RegisterDTO;
import com.dinneconnect.auth.login_register.models.User;
import com.dinneconnect.auth.login_register.services.UserService;

/**
 * REST controller for handling user registration.
 * 
 * @author Sebastian Avendaño Rodriguez
 * @since 2024/11/04
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private UserService service;

    /**
     * Registers a new user.
     * 
     * @param user the registration details
     * @return a map containing a success message
     */
    @PostMapping("/post-user/")
    public Map<String, String> registerUser(@RequestBody RegisterDTO user) {
        User user_db = new User(user);
        service.createUser(user_db);
        Map<String, String> response = new HashMap<>();
        response.put("success", "user registered");
        return response;
    }
}
