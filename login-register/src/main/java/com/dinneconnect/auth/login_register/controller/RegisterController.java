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
 * RegisterController
 * 
 * REST controller for managing user registration. This class provides an
 * endpoint
 * for creating new users in the system by accepting registration details and
 * delegating user creation to the service layer.
 * 
 * Annotations:
 * - @RestController: Indicates that this class is a REST controller, processing
 * HTTP
 * requests and returning JSON responses.
 * - @RequestMapping: Maps the controller to the base path "/api".
 * 
 * Dependencies:
 * - UserService: Handles business logic for user creation.
 * 
 * Author: Sebastian Avendaño Rodriguez
 * Since: 2024/11/04
 * Version: 1.0
 */
@RestController
@RequestMapping("/api")
public class RegisterController {

    /**
     * Service layer dependency for managing user operations.
     * Used to handle the creation of new users.
     */
    @Autowired
    private UserService service;

    /**
     * Registers a new user in the system.
     * 
     * Endpoint: POST /api/post-user/
     * 
     * This method accepts a JSON payload containing user registration details,
     * converts the data to a User entity, and invokes the service layer to persist
     * the user. A success message is returned upon successful registration.
     * 
     * @param user a RegisterDTO object containing the user's registration details
     *             such as username, email, and password.
     * @return a map containing a success message, structured as a JSON response
     *         with a single key-value pair: { "success": "user registered" }.
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
