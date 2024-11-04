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

    public UserRequestDTO() {

    }

    /**
     * Constructs a new ListUserRequestDTO with the specified values.
     * 
     * @param name          the name of the user
     * @param surname       the surname of the user
     * @param email         the email address of the user
     * @param creationDate  the creation date of the user in string format
     * @param verified      the verification status of the user
     * @param reservation   the reservation status of the user
     * @param restaurantIds the ID of the restaurant associated with the user
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
}
