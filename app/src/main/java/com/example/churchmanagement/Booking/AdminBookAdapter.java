package com.example.churchmanagement.Booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.bumptech.glide.Glide;
import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.Api;
import com.example.churchmanagement.databinding.EventCardBinding;

import java.util.ArrayList;
import java.util.List;

public class AdminBookAdapter extends RecyclerView.Adapter<AdminBookAdapter.ViewHolder> {

    Context context;
    List<AllAdminBooking> allAdminBookings=new ArrayList<>();
    RecyclerViewClickInterface recyclerViewClickInterface;


    public AdminBookAdapter(Context content, List<AllAdminBooking> allAdminBookings, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = content;
        this.allAdminBookings = allAdminBookings;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public AdminBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return  new AdminBookAdapter.ViewHolder(EventCardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminBookAdapter.ViewHolder holder, int position) {

        AllAdminBooking allAdminBooking=allAdminBookings.get(position);
       holder.binding.tvEventName.setText(allAdminBooking.getUsername().toString());
        holder.binding.tvEventTitle.setText(allAdminBooking.getBookingFor().toString());
        holder.binding.tvEventStartDate.setText(allAdminBooking.getDate() +" "+allAdminBooking.getTimeOfBooking() );
        holder.binding.llAdminView.setVisibility(View.GONE);

        if(allAdminBooking.getStatus().equalsIgnoreCase("1")){

            holder.binding.confirm.setVisibility(View.GONE);
        }
        holder.binding.tvEventEndDate.setVisibility(View.GONE);
        holder.binding.confirm.setVisibility(View.VISIBLE);
        Glide.with(context).load(APIClient.baseUrl+allAdminBooking.getImage()).into(holder.binding.ivIcon);

    }

    @Override
    public int getItemCount() {
        return allAdminBookings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EventCardBinding binding;

        public ViewHolder(@NonNull EventCardBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"adconfirm");
                }
            });
        }
    }
}
