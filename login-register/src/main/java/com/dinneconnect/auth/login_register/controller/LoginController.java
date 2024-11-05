package com.dinneconnect.auth.login_register.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinneconnect.auth.login_register.DTO.UserRequestDTO;
import com.dinneconnect.auth.login_register.models.User;
import com.dinneconnect.auth.login_register.services.UserService;
import com.dinneconnect.auth.login_register.utilities.UserUtilities;
import com.dinneconnect.auth.login_register.DTO.LoginRequestDTO;
import com.dinneconnect.auth.login_register.DTO.LoginResponseDTO;

/**
 * REST controller for handling user authentication and retrieval.
 * 
 * @author Sebastian Avendaño Rodriguez
 * @since 2024/11/04
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserUtilities utilities;

    /**
     * Authenticates a user.
     * 
     * @param user the user login details
     * @return the login response
     */
    @PostMapping("/auth-user/")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO user) {
        return null; // Implement login logic
    }

    /**
     * Retrieves a list of all users.
     * 
     * @return a list of UserRequestDTOs
     */
    @GetMapping("/users/")
    public List<UserRequestDTO> getUsers() {
        List<User> users = userService.getAllUsers();
        return utilities.UsersToUserDTOs(users);
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param id the ID of the user
     * @return the user details
     */
    @GetMapping("/user/{id}")
    public UserRequestDTO getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return utilities.UserToUserDTO(user);
        } catch (RuntimeException e) {
            return new UserRequestDTO(); // Handle user not found scenario
        }
    }
}
