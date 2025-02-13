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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
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
import com.dinneconnect.auth.login_register.services.UserService;
import com.dinneconnect.auth.login_register.utilities.JWTUtilities;
import com.dinneconnect.auth.login_register.utilities.UserUtilities;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

/**
 * REST Controller for managing user operations after authentication.
 * Provides endpoints for retrieving, updating, and deleting user information.
 * All secured endpoints require JWT authentication via Bearer token.
 * 
 * Security considerations:
 * - All endpoints (except testing ones) require valid JWT token
 * - Tokens must be provided in Authorization header with "Bearer" prefix
 * - Token validation includes expiration, format and signature checks
 * 
 * Common error scenarios:
 * - Token expired
 * - Invalid token format
 * - Malformed token
 * - Invalid signature
 * - Missing or null token
 * 
 * @RestController Indicates that this class serves REST endpoints
 *                 @RequestMapping("/api") Base path for all endpoints in this
 *                 controller
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    /**
     * Service for handling user-related business operations.
     * Manages user data persistence, retrieval and modifications.
     * Injected by Spring's dependency injection.
     */
    @Autowired
    private UserService userService;

    /**
     * Utility class for data transformation operations.
     * Handles conversion between entity and DTO objects.
     * Injected by Spring's dependency injection.
     */
    @Autowired
    private UserUtilities utilities;

    /**
     * 
     * This class provides the user information, this is used for settings purpose
     * 
     * @param authToken JWT Token
     * @return UserResponseDTO
     */
    @GetMapping("/get-user")
    public ResponseEntity<UserResponseDTO> getUserInformation(@RequestHeader("Authorization") String authToken) {
        try {
            if (!authToken.startsWith("Bearer ")) {
                return ResponseEntity.badRequest()
                        .body(new UserResponseDTO("Invalid token format: Bearer prefix required"));
            }

            String token = authToken.substring(7);
            Map<String, Object> claims = JWTUtilities.verifyToken(token);

            if (claims == null || claims.get("sub") == null) {
                return ResponseEntity.badRequest()
                        .body(new UserResponseDTO("Invalid token: missing user information"));
            }

            Long userId;
            try {
                userId = Long.parseLong(claims.get("sub").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest()
                        .body(new UserResponseDTO("Invalid user ID format in token"));
            }

            UserResponseDTO user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new UserResponseDTO("User not found"));
            }

            UserResponseDTO userDTO = utilities.UserToUserDTO(user);
            return ResponseEntity.ok().body(userDTO);

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new UserResponseDTO("Your session has expired"));
        } catch (UnsupportedJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponseDTO("Token format is not supported"));
        } catch (MalformedJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponseDTO("Token is malformed"));
        } catch (SignatureException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new UserResponseDTO("Invalid token signature"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UserResponseDTO("Token cannot be null or empty"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponseDTO("An unexpected error occurred"));
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
    @PostMapping("/update-user/primary/")
    public ResponseEntity<String> updateUserInformation(@RequestBody UpdatePrimaryInfoDTO request,
            @RequestHeader("Authorization") String authToken) {
        try {
            if (authToken.startsWith("Bearer ")) {
                authToken = authToken.substring(7);
                Map<String, Object> info = JWTUtilities.verifyToken(authToken);

                Long code = Long.parseLong((String) info.get("sub"));

                Map<String, Boolean> updt = userService.updatePrimaryInfo(code, request);

                if (updt.get("success")) {
                    return ResponseEntity.ok().body("User information updated");
                }
                return ResponseEntity.badRequest().body("Something went wrong");

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
    @PostMapping("/update-user/password/")
    public ResponseEntity<String> changePassword(@RequestBody HashMap<String, Object> request,
            @RequestHeader("Authorization") String authToken) {
        try {
            if (authToken.startsWith("Bearer ")) {
                authToken = authToken.substring(7);
                Map<String, Object> info = JWTUtilities.verifyToken(authToken);

                Long code = Long.parseLong((String) info.get("sub"));
                Map<String, Boolean> updt = userService.updatePassword(code, (String) request.get("request"));
                if (updt.get("success")) {
                    return ResponseEntity.ok().body("Password updated");
                }
                return ResponseEntity.badRequest().body("Something went wrong");

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
    @DeleteMapping("/delete-user/")
    public ResponseEntity<String> deleteUserInformation(@RequestHeader("Authorization") String authToken) {
        try {
            if (authToken.startsWith("Bearer ")) {
                authToken = authToken.substring(7);
                Map<String, Object> info = JWTUtilities.verifyToken(authToken);

                Long code = Long.parseLong((String) info.get("sub"));

                Map<String, Object> updt = userService.deleteUserById(code);

                if ((Boolean) updt.get("success")) {
                    return ResponseEntity.ok().body("User deleted");
                }
                return ResponseEntity.badRequest().body("Something went wrong");

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
    @GetMapping("/user/all/testing/")
    public List<UserResponseDTO> getUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
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
    public UserResponseDTO getUserById(@PathVariable Long id) {
        try {
            UserResponseDTO user = userService.getUserById(id);
            return utilities.UserToUserDTO(user);
        } catch (RuntimeException e) {
            return new UserResponseDTO();
        }
    }
}
