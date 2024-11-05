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
     * The token should be a JWT in the future.
     * 
     * @param email the email address of the user
     */
    public LoginResponseDTO(String email) {
        this.email = email;
        this.token = "JASHDjahskdhasudyOH"; // Placeholder for JWT implementation
    }

    /**
     * Gets the email address of the user.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the token.
     * 
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     * 
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
}
