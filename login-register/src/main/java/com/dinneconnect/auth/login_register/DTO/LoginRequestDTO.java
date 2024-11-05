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

    private String email;
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
    public LoginRequestDTO(String email, String hashed_password) {
        this.email = email;
        this.hashed_password = hashed_password;
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
     * Gets the hashed password of the user.
     * 
     * @return the hashed_password
     */
    public String getHashed_password() {
        return hashed_password;
    }

    /**
     * Sets the hashed password of the user.
     * 
     * @param hashed_password the hashed password to set
     */
    public void setHashed_password(String hashed_password) {
        this.hashed_password = hashed_password;
    }
}
