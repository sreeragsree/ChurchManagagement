package com.example.churchmanagement.Model;

import com.example.churchmanagement.Ministry.AllMinistry;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultData {

    @SerializedName("All_Ministry")
    @Expose
    private List<AllMinistry> allMinistry = null;

    public List<AllMinistry> getAllMinistry() {
        return allMinistry;
    }

    public void setAllMinistry(List<AllMinistry> allMinistry) {
        this.allMinistry = allMinistry;
    }


}