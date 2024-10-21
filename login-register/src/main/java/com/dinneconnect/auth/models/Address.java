package com.dinneconnect.auth.models;

public class Address {

    private String country;
    private String state;
    private String city;
    private String lat;
    private String lng;
    private String location;

    /**
     * This constructor will create the instance of the class
     * 
     * @param country  Country where the restaurant is
     * @param state    State where the restaurant is
     * @param city     City where the restaurant is
     * @param lat      Latitude where the restaurant is
     * @param lng      Longitude where the restaurant is
     * @param location Location where the restaurant is
     */
    public Address(String country, String state, String city, String lat, String lng, String location) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.lat = lat;
        this.lng = lng;
        this.location = location;
    }

}
