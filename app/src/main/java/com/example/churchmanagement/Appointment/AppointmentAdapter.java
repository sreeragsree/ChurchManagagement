package com.example.churchmanagement.Appointment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.databinding.AppointmentCardBinding;
import com.example.churchmanagement.utils.Utils;

import java.util.List;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    Context context;
    List<AllAppointment> allAppointments;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public AppointmentAdapter(Context context, List<AllAppointment> allAppointments, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.allAppointments = allAppointments;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override

    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(AppointmentCardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {

        AllAppointment appointment = allAppointments.get(position);
        holder.binding.tvApoDate.setText("Date : " + appointment.getApDate());
        holder.binding.tvApoName.setText("Name : " + appointment.getAppointFrom());
        holder.binding.tvApoTime.setText("Time : " + appointment.getApTime());
        holder.binding.tvApoReason.setText("Reason : " + appointment.getReason());

        if (!Utils.getSharedPreference().getString("username", "").equalsIgnoreCase("admin@gmail.com")) {

            holder.binding.ivIconnt.setEnabled(false);


        }

        if (appointment.getStatus().equalsIgnoreCase("1")) {

            holder.binding.ivIcon.setVisibility(View.VISIBLE);

        } else {
            holder.binding.ivIconnt.setVisibility(View.VISIBLE);

        }


    }

    @Override
    public int getItemCount() {
        return allAppointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppointmentCardBinding binding;

        public ViewHolder(@NonNull AppointmentCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.ivIconnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"STUP");
                }
            });
        }
    }
}
