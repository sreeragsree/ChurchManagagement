package com.example.churchmanagement.Ministry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllMinistry {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ministry_name")
    @Expose
    private String ministryName;
    @SerializedName("ministry_category")
    @Expose
    private String ministryCategory;
    @SerializedName("member_limit")
    @Expose
    private String memberLimit;
    @SerializedName("description")
    @Expose
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMinistryName() {
        return ministryName;
    }

    public void setMinistryName(String ministryName) {
        this.ministryName = ministryName;
    }

    public String getMinistryCategory() {
        return ministryCategory;
    }

    public void setMinistryCategory(String ministryCategory) {
        this.ministryCategory = ministryCategory;
    }

    public String getMemberLimit() {
        return memberLimit;
    }

    public void setMemberLimit(String memberLimit) {
        this.memberLimit = memberLimit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
