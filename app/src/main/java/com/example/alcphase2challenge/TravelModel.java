package com.example.alcphase2challenge;

public class TravelModel {

    private String country;
    private String holidayLocation;
    private String amount;
    private String imageURL;

    public TravelModel(String country, String holidayLocation, String amount) {
        this.country = country;
        this.holidayLocation = holidayLocation;
        this.amount = amount;
    }

    public TravelModel() {
    }

    TravelModel(String country, String holidayLocation, String amount, String imageURL) {
        this.country = country;
        this.holidayLocation = holidayLocation;
        this.amount = amount;
        this.imageURL = imageURL;

    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHolidayLocation() {
        return holidayLocation;
    }

    public void setHolidayLocation(String holidayLocation) {
        this.holidayLocation = holidayLocation;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
