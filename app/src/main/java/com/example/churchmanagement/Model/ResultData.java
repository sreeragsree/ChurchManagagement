package com.example.churchmanagement.Model;


import com.example.churchmanagement.Authentication.AllMember;
import com.example.churchmanagement.Events.AllChurchEvent;
import com.example.churchmanagement.Ministry.AllMinistry;
import com.example.churchmanagement.Ministry.MemberListMinistry;
import com.example.churchmanagement.Ministry.MinistryReqStatusId;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultData {

    @SerializedName("All_Church_Events")
    @Expose
    private List<AllChurchEvent> allChurchEvents = null;

    public List<AllChurchEvent> getAllChurchEvents() {
        return allChurchEvents;
    }

    public void setAllChurchEvents(List<AllChurchEvent> allChurchEvents) {
        this.allChurchEvents = allChurchEvents;
    }

    @SerializedName("All_Members")
    @Expose
    private List<AllMember> allMembers = null;

    public List<AllMember> getAllMembers() {
        return allMembers;
    }

    public void setAllMembers(List<AllMember> allMembers) {
        this.allMembers = allMembers;
    }

    @SerializedName("All_Ministry")
    @Expose
    private List<AllMinistry> allMinistry = null;

    public List<AllMinistry> getAllMinistry() {
        return allMinistry;
    }

    public void setAllMinistry(List<AllMinistry> allMinistry) {
        this.allMinistry = allMinistry;
    }

    @SerializedName("MinistryReqStatusId")
    @Expose
    private List<MinistryReqStatusId> ministryReqStatusId = null;

    public List<MinistryReqStatusId> getMinistryReqStatusId() {
        return ministryReqStatusId;
    }

    public void setMinistryReqStatusId(List<MinistryReqStatusId> ministryReqStatusId) {
        this.ministryReqStatusId = ministryReqStatusId;
    }

    @SerializedName("MemberList_Ministry")
    @Expose
    private List<MemberListMinistry> memberListMinistry = null;

    public List<MemberListMinistry> getMemberListMinistry() {
        return memberListMinistry;
    }

    public void setMemberListMinistry(List<MemberListMinistry> memberListMinistry) {
        this.memberListMinistry = memberListMinistry;
    }




}