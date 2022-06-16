package com.example.churchmanagement.Donations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllDonation {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("payee_id")
    @Expose
    private String payeeId;
    @SerializedName("t_id")
    @Expose
    private String tId;
    @SerializedName("at_time")
    @Expose
    private String atTime;
    @SerializedName("amount_rec")
    @Expose
    private String amountRec;
    @SerializedName("amount_for")
    @Expose
    private String amountFor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getAtTime() {
        return atTime;
    }

    public void setAtTime(String atTime) {
        this.atTime = atTime;
    }

    public String getAmountRec() {
        return amountRec;
    }

    public void setAmountRec(String amountRec) {
        this.amountRec = amountRec;
    }

    public String getAmountFor() {
        return amountFor;
    }

    public void setAmountFor(String amountFor) {
        this.amountFor = amountFor;
    }

}