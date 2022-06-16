package com.example.churchmanagement.Events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.databinding.EventCardBinding;
import com.example.churchmanagement.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    Context context;
    List<AllChurchEvent> allChurchEvents = new ArrayList<>();
    RecyclerViewClickInterface recyclerViewClickInterface;

    public EventAdapter(Context context, List<AllChurchEvent> allChurchEvents, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.allChurchEvents = allChurchEvents;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventAdapter.ViewHolder(EventCardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {

        AllChurchEvent event =allChurchEvents.get(position);
        if(!Utils.getSharedPreference().getString("username","").equalsIgnoreCase("admin@gmail.com"))
        {
            holder.binding.llAdminView.setVisibility(View.GONE);

        }
        Glide.with(context).load(APIClient.baseUrl+event.getEventImg()).into(holder.binding.ivIcon);
        holder.binding.tvEventName.setText("Event Name : "+event.getEventName());
        holder.binding.tvEventTitle.setText(event.getEventName());
        holder.binding.tvEventDescription.setText("Description : "+event.getEventDescription());
        holder.binding.tvEventStartDate.setText("Start Date : "+event.getStartDate());
        holder.binding.tvEventEndDate.setText("End Date : "+event.getEndDate());

    }

    @Override
    public int getItemCount() {
        return allChurchEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EventCardBinding binding;

        public ViewHolder(@NonNull EventCardBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.adEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"EDIT");
                }
            });
            binding.edDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"DELETE");
                }
            });


        }
    }
}
