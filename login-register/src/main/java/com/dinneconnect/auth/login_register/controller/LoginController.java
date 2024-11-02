package com.dinneconnect.auth.login_register.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinneconnect.auth.login_register.models.LoginRequest;
import com.dinneconnect.auth.login_register.models.LoginResponse;

@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/sign-in/")
    public LoginResponse login(@RequestBody LoginRequest authenticated) {

        // This need to be changed when the JWT system is it connected.

        if (authenticated.authUser()) {
            return new LoginResponse("ajshdjashdjahsd", "User logged succesfully");
        }
        return new LoginResponse(null, "User credentials are incorrect");
    }

    @GetMapping("/mark/")
    public String sayHello() {
        return "Hello, World!";
    }
}