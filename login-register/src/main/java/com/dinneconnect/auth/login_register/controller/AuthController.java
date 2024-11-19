package com.dinneconnect.auth.login_register.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinneconnect.auth.login_register.DTO.UserRequestDTO;
import com.dinneconnect.auth.login_register.models.User;
import com.dinneconnect.auth.login_register.services.UserService;
import com.dinneconnect.auth.login_register.utilities.JWTUtilities;
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
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserUtilities utilities;

    @GetMapping("/verify-token/")
    public Map<String, Object> verify(@RequestHeader("Authorization") String authToken) {
        // Remover el prefijo "Bearer" si existe
        if (authToken.startsWith("Bearer ")) {
            authToken = authToken.substring(7);
        }

        // Verifica el token y retorna los claims
        return JWTUtilities.verifyToken(authToken);
    }

    /**
     * Authenticates a user.
     * 
     * @param user the user login details
     * @return the login response
     */
    @PostMapping("/generate-token/")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO user) {

        User user_auth = userService.getUserByEmail(user.getEmail());
        if (user_auth.verifyPassword(user.getHashed_password())){
            LoginResponseDTO login = new LoginResponseDTO(user.getEmail());
            return login;
        }

        return new LoginResponseDTO();
         // Implement login logic
    }

    // NEEDED TO IMPLEMENT ENDPOINT CONFIGURATIONS BASED ON ROLES

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
