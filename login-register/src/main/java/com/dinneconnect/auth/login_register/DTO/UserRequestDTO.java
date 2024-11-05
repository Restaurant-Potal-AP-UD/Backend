package com.dinneconnect.auth.login_register.DTO;

/**
 * This module handles data transfer objects (DTOs) for user-related operations.
 * In general, to show low-level user information.
 * 
 * @version 1.0
 * @since 2024-11-02
 * @author Sebasian Avendaño Rodriguez
 */
public class UserRequestDTO {

    private String name;
    private String surname;
    private String email;
    private String creationDate;
    private Boolean verified;
    private Boolean reservation;
    private Integer restaurantIds;

    /**
     * Constructs a new UserRequestDTO.
     * 
     * @param name          the user's first name
     * @param surname       the user's surname
     * @param email         the user's email address
     * @param creationDate  the date the user was created
     * @param verified      whether the user is verified
     * @param reservation   whether the user has reservations
     * @param restaurantIds the user's associated restaurant IDs
     */
    public UserRequestDTO(String name, String surname, String email, String creationDate, Boolean verified,
            Boolean reservation, Integer restaurantIds) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.creationDate = creationDate;
        this.verified = verified;
        this.reservation = reservation;
        this.restaurantIds = restaurantIds;
    }

    public UserRequestDTO() {

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

    /**
     * Gets whether the user is verified.
     * 
     * @return the verified
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     * Sets whether the user is verified.
     * 
     * @param verified the verified status to set
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    /**
     * Gets whether the user has reservations.
     * 
     * @return the reservation
     */
    public Boolean getReservation() {
        return reservation;
    }

    /**
     * Sets whether the user has reservations.
     * 
     * @param reservation the reservation status to set
     */
    public void setReservation(Boolean reservation) {
        this.reservation = reservation;
    }

    /**
     * Gets the user's associated restaurant IDs.
     * 
     * @return the restaurantIds
     */
    public Integer getRestaurantIds() {
        return restaurantIds;
    }

    /**
     * Sets the user's associated restaurant IDs.
     * 
     * @param restaurantIds the restaurantIds to set
     */
    public void setRestaurantIds(Integer restaurantIds) {
        this.restaurantIds = restaurantIds;
    }
}
