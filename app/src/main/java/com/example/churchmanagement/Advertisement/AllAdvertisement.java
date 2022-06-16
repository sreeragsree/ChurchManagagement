package com.example.churchmanagement.Advertisement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllAdvertisement {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("advertisement_name")
    @Expose
    private String advertisementName;
    @SerializedName("advertisement_description")
    @Expose
    private String advertisementDescription;
    @SerializedName("advertisement_img")
    @Expose
    private String advertisementImg;
    @SerializedName("posted_by")
    @Expose
    private String postedBy;
    @SerializedName("active")
    @Expose
    private String active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdvertisementName() {
        return advertisementName;
    }

    public void setAdvertisementName(String advertisementName) {
        this.advertisementName = advertisementName;
    }

    public String getAdvertisementDescription() {
        return advertisementDescription;
    }

    public void setAdvertisementDescription(String advertisementDescription) {
        this.advertisementDescription = advertisementDescription;
    }

    public String getAdvertisementImg() {
        return advertisementImg;
    }

    public void setAdvertisementImg(String advertisementImg) {
        this.advertisementImg = advertisementImg;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}