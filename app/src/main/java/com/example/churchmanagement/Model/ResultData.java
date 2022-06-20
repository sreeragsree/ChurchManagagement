package com.example.churchmanagement.Model;


import com.example.churchmanagement.Advertisement.AllAdvertisement;
import com.example.churchmanagement.Appointment.AllAppointment;
import com.example.churchmanagement.Authentication.AllMember;
import com.example.churchmanagement.Booking.AllAdminBooking;
import com.example.churchmanagement.Booking.AllAmenity;
import com.example.churchmanagement.Donations.AllDonation;
import com.example.churchmanagement.Events.AllChurchEvent;
import com.example.churchmanagement.Ministry.AllMinistry;
import com.example.churchmanagement.Ministry.MemberListMinistry;
import com.example.churchmanagement.Ministry.MinistryReqStatusId;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultData {

    @SerializedName("All_Admin_booking")
    @Expose
    private List<AllAdminBooking> allAdminBooking = null;

    public List<AllAdminBooking> getAllAdminBooking() {
        return allAdminBooking;
    }

    public void setAllAdminBooking(List<AllAdminBooking> allAdminBooking) {
        this.allAdminBooking = allAdminBooking;
    }

    @SerializedName("All_Amenities")
    @Expose
    private List<AllAmenity> allAmenities = null;

    public List<AllAmenity> getAllAmenities() {
        return allAmenities;
    }

    public void setAllAmenities(List<AllAmenity> allAmenities) {
        this.allAmenities = allAmenities;
    }

    @SerializedName("All_Donations")
    @Expose
    private List<AllDonation> allDonations = null;

    public List<AllDonation> getAllDonations() {
        return allDonations;
    }

    public void setAllDonations(List<AllDonation> allDonations) {
        this.allDonations = allDonations;
    }


    @SerializedName("All_Appointments")
    @Expose
    private List<AllAppointment> allAppointments = null;

    public List<AllAppointment> getAllAppointments() {
        return allAppointments;
    }

    public void setAllAppointments(List<AllAppointment> allAppointments) {
        this.allAppointments = allAppointments;
    }

    @SerializedName("All_Advertisement")
    @Expose
    private List<AllAdvertisement> allAdvertisement = null;

    public List<AllAdvertisement> getAllAdvertisement() {
        return allAdvertisement;
    }

    public void setAllAdvertisement(List<AllAdvertisement> allAdvertisement) {
        this.allAdvertisement = allAdvertisement;
    }

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