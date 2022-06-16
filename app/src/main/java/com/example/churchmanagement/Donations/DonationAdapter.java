package com.example.churchmanagement.Donations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.churchmanagement.Booking.BookingAdapter;
import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.databinding.AllbookingCardBinding;
import com.example.churchmanagement.databinding.DonationCardBinding;

import java.util.ArrayList;
import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {

    Context context;
    List<AllDonation> allDonations=new ArrayList<>();
    RecyclerViewClickInterface recyclerViewClickInterface;

    public DonationAdapter(Context context, List<AllDonation> allDonations, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.allDonations = allDonations;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public DonationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonationAdapter.ViewHolder(DonationCardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonationAdapter.ViewHolder holder, int position) {
        AllDonation allDonation=allDonations.get(position);
        holder.donationCardBinding.amount.setText("Rs "+allDonation.getAmountRec());
        holder.donationCardBinding.payee.setText(allDonation.getPayeeId());
        holder.donationCardBinding.paymentFor.setText(allDonation.getAmountFor());

    }

    @Override
    public int getItemCount() {
        return allDonations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        DonationCardBinding donationCardBinding;
        public ViewHolder(@NonNull DonationCardBinding donationCardBinding) {
            super(donationCardBinding.getRoot());
            this.donationCardBinding=donationCardBinding;
        }
    }
}
