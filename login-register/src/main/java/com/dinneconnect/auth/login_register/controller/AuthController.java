package com.dinneconnect.auth.login_register.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinneconnect.auth.login_register.services.UserService;
import com.dinneconnect.auth.login_register.utilities.JWTUtilities;

import com.dinneconnect.auth.login_register.DTO.LoginRequestDTO;
import com.dinneconnect.auth.login_register.DTO.LoginResponseDTO;
import com.dinneconnect.auth.login_register.DTO.UserResponseDTO;

/**
 * AuthController is a REST controller that handles user authentication
 * and token verification for login and registration processes.
 * It provides endpoints for generating tokens and verifying them.
 * 
 * @author Sebastian Avendaño Rodriguez
 * @since 2024/11/04
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    /**
     * The UserService instance used for user-related operations.
     */
    @Autowired
    private UserService userService;

    /**
     * Endpoint to verify the provided JWT token.
     * 
     * @param authToken the authorization token sent in the request header
     * @return a map containing the verification result of the token
     */
    @GetMapping("/verify-token/")
    public Map<String, Object> verify(@RequestHeader("Authorization") String authToken) {
        // Check if the token starts with "Bearer " and remove it if present
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }

        // Verify the token using JWTUtilities and return the result
        return JWTUtilities.verifyToken(authToken);
    }

    /**
     * Endpoint for user login, which authenticates a user and generates a token.
     * 
     * @param user the login request containing user email and hashed password
     * @return a LoginResponseDTO containing the user's ID if authentication is
     *         successful,
     *         or an empty response if authentication fails
     */
    @PostMapping("/generate-token/")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO user) {

        UserResponseDTO user_auth = userService.getUserByUsername(user.getUsername(), user.getHashed_password());
        if (user_auth != null) {
            LoginResponseDTO login = new LoginResponseDTO((Long) user_auth.getCode(),
                    (String) user_auth.getUsername());
            return ResponseEntity.ok().body(login);
        } else {
            return ResponseEntity.badRequest().body(new LoginResponseDTO());
        }

    }

}
