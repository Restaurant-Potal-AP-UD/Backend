package com.dinneconnect.auth.login_register.DTO;

/**
 * This module handles data transfer objects (DTOs) for user-related operations.
 * In general, to show low-level user information.
 * 
 * @version 1.0
 * @since 2024-11-02
 * @author Sebasian Avendaño Rodriguez
 */
public class UserResponseDTO {

    @SuppressWarnings("unused")
    private String message;

    private Long code;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String creationDate;

    /**
     * Constructs a new UserRequestDTO.
     * 
     * @param name         the user's first name
     * @param surname      the user's surname
     * @param email        the user's email address
     * @param creationDate the date the user was created
     * 
     */
    public UserResponseDTO(Long code, String name, String surname, String username, String email,
            String creationDate) {
        this.code = code;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.creationDate = creationDate;
    }

    public UserResponseDTO(String message) {
        this.message = message;
    }

    public UserResponseDTO() {

    }

    /**
     * Gets the user's first name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's first name.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's surname.
     * 
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the user's surname.
     * 
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's email address.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the creation date.
     * 
     * @return the creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date.
     * 
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCode() {
        return code;
    }

}
