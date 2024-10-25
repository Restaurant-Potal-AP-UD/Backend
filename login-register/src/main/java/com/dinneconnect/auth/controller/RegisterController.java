package com.dinneconnect.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dinneconnect.auth.models.RegisterResponse;
import com.dinneconnect.auth.models.User;

@RestController
public class RegisterController {

    private User user;

    @PostMapping("sign-up")
    public RegisterResponse registerUser(@RequestParam String name,
            @RequestParam String surname,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String password) {

        // We need to find a way to storage data, also, this will be
        // a form to create a new user that isn't be repeated
        this.user = new User(name, surname, username, email, phoneNumber, password);
        return new RegisterResponse("User created succesfully");
    }

    public void addUserDB() {
        // Add user to database
    }
}
