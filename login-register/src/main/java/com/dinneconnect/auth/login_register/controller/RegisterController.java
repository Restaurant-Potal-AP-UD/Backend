package com.dinneconnect.auth.login_register.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinneconnect.auth.login_register.DTO.RegisterDTO;
import com.dinneconnect.auth.login_register.models.User;

@RestController
@RequestMapping("/api")
public class RegisterController {

    private User user;

    @PostMapping("/post-user/")
    public Map<String, String> registerUser(@RequestBody RegisterDTO user) {
        Map<String, String> response = new HashMap<>();
        response.put("success", "user registered");
        return response;
    }

    @GetMapping("/mark/")
    public String mark() {
        return "Puta";
    }
}
