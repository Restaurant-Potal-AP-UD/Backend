package com.dinneconnect.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dinneconnect.auth.models.LoginRequest;
import com.dinneconnect.auth.models.LoginResponse;

@RestController
public class LoginController {

    @PostMapping("/sign-in/")
    public LoginResponse login(@RequestBody LoginRequest authenticated) {

        // This need to be changed when the JWT system is it connected.

        if (authenticated.authUser()) {
            return new LoginResponse("ajshdjashdjahsd", "User logged succesfully");
        }
        return new LoginResponse(null, "User credentials are incorrect");
    }
}