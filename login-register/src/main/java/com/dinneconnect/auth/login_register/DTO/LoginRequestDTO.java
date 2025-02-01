package com.dinneconnect.auth.login_register.DTO;

/**
 * This module handles data transfer objects (DTOs) for authentication
 * operations.
 * 
 * @version 1.0
 * @since 2024-11-02
 * @author Sebastian Avendaño Rodriguez
 */
public class LoginRequestDTO {

    private String username;
    private String hashed_password;

    /**
     * Default constructor for LoginRequestDTO.
     */
    public LoginRequestDTO() {
    }

    /**
     * Constructs a new LoginRequestDTO with the specified values.
     * 
     * @param email           the email address of the user
     * @param hashed_password the hashed password of the user
     */
    public LoginRequestDTO(String username, String hashed_password) {
        this.username = username;
        this.hashed_password = hashed_password;
    }

    /**
     * Gets the email address of the user.
     * 
     * @return the email
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the email address of the user.
     * 
     * @param email the email to set
     */
    public void setEmail(String username) {
        this.username = username;
    }

    /**
     * Gets the hashed password of the user.
     * 
     * @return the hashed_password
     */
    public String getHashed_password() {
        return hashed_password;
    }

    public Boolean verifyPassword(String password) {
        if (this.hashed_password.equals(password)) {
            return true;
        }
        return false;
    }
}
