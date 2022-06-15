package com.example.churchmanagement.Booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.churchmanagement.Appointment.AppointmentAdapter;
import com.example.churchmanagement.databinding.AllbookingCardBinding;
import com.example.churchmanagement.databinding.AppointmentCardBinding;


public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder>{
    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AllbookingCardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AllbookingCardBinding binding;

        public ViewHolder(@NonNull AllbookingCardBinding binding) {
            super(binding.getRoot());
        }
    }
}
