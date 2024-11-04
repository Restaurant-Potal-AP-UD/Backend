package com.dinneconnect.auth.login_register.DTO;

/**
 * This module handles data transfer objects (DTOs) for user authentication
 * responses.
 * 
 * @version 1.0
 * @since 2024-11-02
 * @author Sebastian Avendaño Rodriguez
 */
public class LoginResponseDTO {

    private String email;
    private String token;

    /**
     * Constructs a new LoginResponseDTO with the specified email.
     * 
     * @param email the email address of the user
     */
    public LoginResponseDTO(String email) {
        // Later on should be the JWT implementation

        this.email = email;
        this.token = "JASHDjahskdhasudyOH";
    }
}
