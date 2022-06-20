package com.example.churchmanagement.Booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllAdminBooking {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private Object username;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time_of_booking")
    @Expose
    private String timeOfBooking;
    @SerializedName("amen_id")
    @Expose
    private String amenId;
    @SerializedName("booking_for")
    @Expose
    private String bookingFor;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("image")
    @Expose
    private String image;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getUsername() {
        return username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeOfBooking() {
        return timeOfBooking;
    }

    public void setTimeOfBooking(String timeOfBooking) {
        this.timeOfBooking = timeOfBooking;
    }

    public String getAmenId() {
        return amenId;
    }

    public void setAmenId(String amenId) {
        this.amenId = amenId;
    }

    public String getBookingFor() {
        return bookingFor;
    }

    public void setBookingFor(String bookingFor) {
        this.bookingFor = bookingFor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}