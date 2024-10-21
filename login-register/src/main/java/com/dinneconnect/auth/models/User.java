package com.dinneconnect.auth.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a User in the system with personal details and associated
 * restaurants.
 * This class holds user information such as name, surname, username, email,
 * phone number,
 * and password. It also manages the user's associated restaurants and provides
 * methods
 * to update the user's password and retrieve restaurant information.
 * 
 * @author Sebastian Avendaño Rodriguez
 * @since 20/10/2024
 * @version 1.0
 * 
 */

public class User {
    private String name;
    private String surname;
    public String username;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDateTime creationDate;
    private Boolean activeReservation = false;
    private Boolean verified = false;
    private Boolean isActive = false;
    private List<Restaurant> restaurants = new ArrayList<>();

    /**
     * Constructs a new User with the specified details.
     * 
     * @param name        The name of the user.
     * @param surname     The surname of the user.
     * @param username    The username of the user.
     * @param email       The email address of the user.
     * @param phoneNumber The phone number of the user.
     * @param password    The password of the user.
     * @param restaurant  The primary restaurant associated with the user.
     * @param restaurants A list of restaurants owned by the user.
     */
    public User(String name, String surname, String username, String email, String phoneNumber,
            String password, Restaurant restaurant) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.creationDate = LocalDateTime.now();
    }

    /**
     * This class represents the restaurant creation of the user.
     * 
     * @param restaurantName Restaurant name
     * @param countryId      This should be the alpha_2 country notation getted from
     *                       Geolocation API
     * @param stateName      This should be the administrative_area_level_1 getted
     *                       from
     *                       Geolocation API
     * @param cityName       This should be the administrative_area_level_2 getted
     *                       from
     *                       Geolocation API
     * @param lat
     * @param lng
     * @param location       This should be the location getted from
     *                       Geolocation API
     */
    public void setRestaurant(String restaurantName, String countryId, String stateName, String cityName, String lat,
            String lng, String location) {
        Restaurant restaurant = new Restaurant(restaurantName, this.username, countryId, stateName, cityName, lat, lng,
                location);
        this.restaurants.add(restaurant);

        // NOTE: we should create groups, so every user that got any restaurant should
        // be in restaunrat Owner Group

        System.out.println("Restaurant succesfully created");
    }

    /**
     * Sets a new password for the user after verifying the current password.
     * 
     * @param rawPassword The current password of the user.
     * @param newPassword The new password to be set.
     */
    public void setNewPassword(String rawPassword, String newPassword) {
        if (!this.password.equals(rawPassword)) {
            // This should be changed, this should launch a status_code 400, due to the
            // incorrect data
            System.out.println("The password is incorrect");
        } else {
            this.password = newPassword;
            // This should raise status_code 200 OK
            System.out.println("The password has been changed");
        }
    }

    /**
     * Retrieves a map containing restaurant details, including the restaurant name,
     * the owner's username, and a list of bookings for that restaurant.
     * 
     * @param user user who want to access to this method
     * @return A map where the key is the restaurant name and the value is another
     *         map. The inner map has the owner's username as the key and a list of
     *         bookings as the value.
     */
    public Map<String, Map<String, List<Booking>>> getRestaurantDetails(User user) {
        Map<String, Map<String, List<Booking>>> restaurantDetails = new HashMap<>();

        // Iterate over each restaurant associated with the user
        for (Restaurant restaurant : restaurants) {
            // Get the name of the restaurant
            String restaurantName = restaurant.restaurantName;
            // Assume the owner is the current user
            String ownerUsername = this.username;

            // Get the list of bookings for the restaurant
            List<Booking> bookings = restaurant.getBookings(user); // Assuming a method exists to get bookings

            // Create a map to store the owner's username and their bookings
            Map<String, List<Booking>> ownerBookings = new HashMap<>();
            ownerBookings.put(ownerUsername, bookings);

            // Add the inner map to the main map using the restaurant name as the key
            restaurantDetails.put(restaurantName, ownerBookings);
        }

        return restaurantDetails; // Return the populated map
    }
}
