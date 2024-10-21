package com.dinneconnect.auth.models;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * Represents a booking made by a user for a specific table.
 * 
 * @author Sebastian Avendaño Rodriguez
 * @since 20/10/2024
 * @version 1.0
 * 
 */
public class Booking {

    private Integer people; // Number of people for the booking
    private String owner; // Name of the person who made the booking
    private LocalDateTime date; // Date and time of the booking
    private Integer tableId; // Identifier for the table being booked

    /**
     * Constructs a Booking instance with the specified number of people, owner, and
     * table ID.
     *
     * @param people  the number of people for the booking
     * @param owner   the name of the person making the booking
     * @param tableId the ID of the table being booked
     */
    public Booking(Integer people, String owner, Integer tableId) {
        this.people = people;
        this.owner = owner;
        this.tableId = tableId;
    }

    /**
     * Sets the date and time for the booking.
     * The date should be in the format "YYYY/MM/DD/HH/MM".
     *
     * @param date the date and time string to set for the booking
     */
    public void setDate(String date) {
        String[] format_date = date.split("/");
        this.date = LocalDateTime.of(Integer.parseInt(format_date[0]),
                Month.of(Integer.parseInt(format_date[1])),
                Integer.parseInt(format_date[2]),
                Integer.parseInt(format_date[3]),
                Integer.parseInt(format_date[4]),
                0, 0);
    }
}
