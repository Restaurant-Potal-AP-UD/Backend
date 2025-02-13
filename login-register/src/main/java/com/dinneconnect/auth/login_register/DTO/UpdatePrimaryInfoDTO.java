package com.dinneconnect.auth.login_register.DTO;

/**
 * Data Transfer Object (DTO) for updating primary user information.
 * This class is used to encapsulate the primary information of a user,
 * including their name, surname, username, and email address.
 */
public class UpdatePrimaryInfoDTO {
    private String name;
    private String surname;
    private String email;

    /**
     * Constructs a new UpdatePrimaryInfoDTO with the specified user information.
     *
     * @param name    the name of the user
     * @param surname the surname of the user
     * @param email   the email address of the user
     */
    public UpdatePrimaryInfoDTO(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    /**
     * Retrieves the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the surname of the user.
     *
     * @return the surname of the user
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the user.
     *
     * @param surname the new surname of the user
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the new email address of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
