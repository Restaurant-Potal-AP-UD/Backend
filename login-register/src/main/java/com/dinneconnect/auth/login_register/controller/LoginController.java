/**
 * Package com.dinneconnect.auth.login_register.controller
 * 
 * This package contains controllers for handling user authentication and management
 * functionalities, including login, registration, and operations such as updating 
 * primary user information, changing passwords, retrieving user details, and deleting users.
 * 
 * The controllers act as an entry point for the REST API, processing HTTP requests,
 * delegating tasks to service layers, and managing JWT-based authentication.
 */

package com.dinneconnect.auth.login_register.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinneconnect.auth.login_register.DTO.UpdatePrimaryInfoDTO;
import com.dinneconnect.auth.login_register.DTO.UserResponseDTO;
import com.dinneconnect.auth.login_register.models.User;
import com.dinneconnect.auth.login_register.services.UserService;
import com.dinneconnect.auth.login_register.utilities.JWTUtilities;
import com.dinneconnect.auth.login_register.utilities.UserUtilities;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

/**
 * LoginController
 * 
 * This controller handles all user-related actions, including retrieving user
 * details,
 * updating user information, changing passwords, and deleting user accounts.
 * It uses JWT-based authentication to secure API endpoints.
 * 
 * Annotations:
 * - @RestController: Marks this class as a REST controller to handle HTTP
 * requests.
 * - @RequestMapping: Maps all API endpoints to the "/api" base path.
 * 
 * Dependencies:
 * - UserService: Provides business logic for user-related operations.
 * - UserUtilities: Converts User objects to UserResponseDTO objects.
 * - JWTUtilities: Validates and parses JWT tokens.
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    /**
     * Utility class for handling JSON Web Tokens (JWTs), including token
     * validation, parsing, and extraction of claims.
     */

    /**
     * Service for handling user-related operations, such as retrieving,
     * updating, and deleting user information.
     */
    @Autowired
    private UserService userService;

    /**
     * Utility class for converting User entities to UserResponseDTO objects
     * and performing other user-related transformations.
     */
    @Autowired
    private UserUtilities utilities;

    /**
     * Retrieves the information of the currently authenticated user.
     * 
     * @param authToken the JWT token provided in the Authorization header
     * @return a ResponseEntity containing the user details or an error message
     */
    @GetMapping("/user/")
    public ResponseEntity<UserResponseDTO> getUserInformation(@RequestHeader("Authorization") String authToken) {

        try {
            if (authToken.startsWith("Bearer ")) {
                authToken = authToken.substring(7);
                Map<String, Object> info = JWTUtilities.verifyToken(authToken);

                UUID uuid = UUID.fromString((String) info.get("sub"));
                User user = userService.getUserById(uuid);

                UserResponseDTO userDTO = utilities.UserToUserDTO(user);
                return ResponseEntity.ok().body(userDTO);
            } else {
                return ResponseEntity.badRequest().body(new UserResponseDTO("Something went wrong"));
            }
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new UserResponseDTO("Your session has expired"));
        } catch (UnsupportedJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponseDTO("Token format is not supported"));
        } catch (MalformedJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponseDTO("Token it's bad formatted"));
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new UserResponseDTO("Token has invalid sign"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponseDTO("Token it's null"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponseDTO(e.getMessage()));
        }
    }

    /**
     * Updates the primary information of the currently authenticated user.
     * 
     * @param request   the UpdatePrimaryInfoDTO object containing new user
     *                  information
     * @param authToken the JWT token provided in the Authorization header
     * @return a ResponseEntity containing a success message or an error message
     */
    @PostMapping("/user/primary/")
    public ResponseEntity<String> updateUserInformation(@RequestBody UpdatePrimaryInfoDTO request,
            @RequestHeader("Authorization") String authToken) {
        try {
            if (authToken.startsWith("Bearer ")) {
                authToken = authToken.substring(7);
                Map<String, Object> info = JWTUtilities.verifyToken(authToken);

                UUID uuid = UUID.fromString((String) info.get("sub"));
                return userService.updatePrimaryInfo(uuid, request);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The token it's no valid");
            }
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Your session has expired");
        } catch (UnsupportedJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token format is not supported");
        } catch (MalformedJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token it's bad formatted");
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has invalid sign");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Token it's null");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Changes the password of the currently authenticated user.
     * 
     * @param request   the UpdatePasswordDTO object containing the new password
     * @param authToken the JWT token provided in the Authorization header
     * @return a ResponseEntity containing a success message or an error message
     */
    @PostMapping("/user/password/")
    public ResponseEntity<String> changePassword(@RequestBody String request,
            @RequestHeader("Authorization") String authToken) {
        try {
            if (authToken.startsWith("Bearer ")) {
                authToken = authToken.substring(7);
                Map<String, Object> info = JWTUtilities.verifyToken(authToken);

                UUID uuid = UUID.fromString((String) info.get("sub"));
                return userService.updatePassword(uuid, request);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The token it's no valid");
            }
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token has been expired");
        } catch (UnsupportedJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token format it's not compatible");
        } catch (MalformedJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token it's bad formatted");
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has invalid sign");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Token it's null");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes the currently authenticated user from the system.
     * 
     * @param authToken the JWT token provided in the Authorization header
     * @return a ResponseEntity containing a success message or an error message
     */
    @DeleteMapping("/user/")
    public ResponseEntity<String> deleteUserInformation(@RequestHeader("Authorization") String authToken) {
        try {
            if (authToken.startsWith("Bearer ")) {
                authToken = authToken.substring(7);
                Map<String, Object> info = JWTUtilities.verifyToken(authToken);

                UUID uuid = UUID.fromString((String) info.get("sub"));
                userService.deleteUserById(uuid);
                return ResponseEntity.ok().body("User deleted");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The token it's no valid");
            }
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token has been expired");
        } catch (UnsupportedJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token format it's not compatible");
        } catch (MalformedJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token it's bad formatted");
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has invalid sign");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Token it's null");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    /*
     * 
     * GENERAL USER METHODS TO RETRIEVE INFORMATION GLOBALLY
     * 
     * THIS SHOULD BE OFF ON PROD, OR, AT LEAST, SHOULD BE USED WITH CAUTION IN
     * ROLES
     * 
     */

    /**
     * Retrieves a list of all users in the system.
     * This endpoint is for testing purposes and should be protected in production.
     * 
     * @return a list of UserResponseDTO objects containing user details
     */
    @GetMapping("/user/all/testing")
    public List<UserResponseDTO> getUsers() {
        List<User> users = userService.getAllUsers();
        return utilities.UsersToUserDTOs(users);
    }

    /**
     * Retrieves the details of a user by their unique ID.
     * 
     * @param id the unique ID of the user to retrieve
     * @return a UserResponseDTO containing the user details or an empty object if
     *         the user is not found
     */
    @GetMapping("/user/unique/{id}")
    public UserResponseDTO getUserById(@PathVariable UUID id) {
        try {
            User user = userService.getUserById(id);
            return utilities.UserToUserDTO(user);
        } catch (RuntimeException e) {
            return new UserResponseDTO(); // Handle user not found scenario
        }
    }
}
