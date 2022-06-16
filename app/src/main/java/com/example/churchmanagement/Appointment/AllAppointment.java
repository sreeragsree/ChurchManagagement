package com.example.churchmanagement.Appointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllAppointment {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("appoint_from")
    @Expose
    private String appointFrom;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("ap_date")
    @Expose
    private String apDate;
    @SerializedName("ap_time")
    @Expose
    private String apTime;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppointFrom() {
        return appointFrom;
    }

    public void setAppointFrom(String appointFrom) {
        this.appointFrom = appointFrom;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApDate() {
        return apDate;
    }

    public void setApDate(String apDate) {
        this.apDate = apDate;
    }

    public String getApTime() {
        return apTime;
    }

    public void setApTime(String apTime) {
        this.apTime = apTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}