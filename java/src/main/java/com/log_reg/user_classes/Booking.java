/**
 * This class will be the main blueprint for every reservation into the restaurants, a way to control
 * the flow of data.
 * 
 * @author Sebastian Avendano
 * @version 1.0
 * @since 2024/10/08
 */
package com.log_reg.user_classes;

import java.time.*;

public class Booking {
    private Integer totalPeople;
    private User userOwner;
    private LocalDateTime date;
    public Integer tableId;

    /**
     * Class builder to create a new instance of Booking Class with the detailed
     * specifications.
     *
     * @param totalPeople Total people that will be in the booking.
     * @param userOwner   The account that make the actual reservation.
     * @param date        Date and time of the reservation.
     * @param tableId     The id of the table where the people should stay.
     */
    public Booking(Integer totalPeople, User userOwner, LocalDateTime date, Integer tableId) {
        this.totalPeople = totalPeople;
        this.userOwner = userOwner;
        this.date = date;
        this.tableId = tableId;
    }

    /**
     * Displays the date of the booking in the console.
     */
    public void showDate() {
        System.out.println(this.date.toString());
    }

    /**
     * Displays the owner of the booking in the console.
     */
    public void showOwner() {
        System.out.println(this.userOwner.toString());
    }

    /**
     * Returns a string representation of the booking object, including the total
     * number of people,
     * the user owner, the date, and the table ID.
     *
     * @return a string containing the details of the booking.
     */
    @Override
    public String toString() {
        return "Total people: " + this.totalPeople + "\n" +
                "User owner: " + this.userOwner.toString() + "\n" +
                "Date: " + this.date.toString() + "\n" +
                "Table ID: " + this.tableId;
    }

}
