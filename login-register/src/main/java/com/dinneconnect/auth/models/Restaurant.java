package com.dinneconnect.auth.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a restaurant with its details such as name, owner, address, and
 * bookings.
 * This class allows the management of restaurant bookings and provides access
 * to the restaurant's information.
 * 
 * @author Sebastian Avendaño Rodriguez
 * @since 20/10/2024
 * @version 1.0
 * 
 */
public class Restaurant {

    public String restaurantName; // The name of the restaurant
    private String owner; // The username of the restaurant owner
    private Address address; // The address of the restaurant
    private List<Booking> bookings = new ArrayList<>(); // List of bookings for the restaurant

    /**
     * Constructs a new Restaurant instance with the specified details.
     *
     * @param restaurantName the name of the restaurant
     * @param user           the username of the owner
     * @param countryId      the country identifier
     * @param stateName      the name of the state
     * @param cityName       the name of the city
     * @param lat            the latitude of the restaurant location
     * @param lng            the longitude of the restaurant location
     * @param location       the specific location description of the restaurant
     */
    public Restaurant(String restaurantName, String user, String countryId, String stateName, String cityName,
            String lat, String lng, String location) {
        this.restaurantName = restaurantName;
        this.owner = user;
        this.address = new Address(countryId, stateName, cityName, lat, lng, location);
    }

    /**
     * Retrieves the list of bookings for the restaurant.
     * This method returns the list of bookings only if the user requesting the
     * bookings is the owner of the restaurant.
     *
     * @param user the user requesting the bookings
     * @return a list of bookings if the user is the owner; otherwise, an empty list
     */
    public List<Booking> getBookings(User user) {
        if (!this.owner.equals(user.username)) {
            return new ArrayList<>();
        } else {
            return bookings;
        }
    }
}