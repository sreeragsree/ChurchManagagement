package com.example.churchmanagement.Advertisement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.databinding.AdvertisementcardBinding;
import com.example.churchmanagement.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.ViewHolder> {

    Context context;
    List<AllAdvertisement> list = new ArrayList<>();
    RecyclerViewClickInterface recyclerViewClickInterface;
    String req_type;

    public AdvertisementAdapter(Context context, List<AllAdvertisement> list, RecyclerViewClickInterface recyclerViewClickInterface, String req_type) {
        this.context = context;
        this.list = list;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
        this.req_type = req_type;
    }

    @NonNull
    @Override
    public AdvertisementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AdvertisementcardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementAdapter.ViewHolder holder, int position) {
        AllAdvertisement allAdvertisement = list.get(position);
        holder.binding.adverDescription.setText(allAdvertisement.getAdvertisementDescription());
        holder.binding.tvCampname.setText(allAdvertisement.getAdvertisementName());
        holder.binding.tvPostedBy.setText(allAdvertisement.getPostedBy());
        Glide.with(context).load(APIClient.baseUrl + allAdvertisement.getAdvertisementImg()).into(holder.binding.ivBanner);
       if(Utils.getSharedPreference().getString("username","").equalsIgnoreCase(allAdvertisement.getPostedBy())){
           holder.binding.ivedit.setVisibility(View.VISIBLE);
           holder.binding.ivDelete.setVisibility(View.VISIBLE);

       }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AdvertisementcardBinding binding;

        public ViewHolder(@NonNull AdvertisementcardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(), "DELETE");
                }
            });

            binding.ivedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(), "EDIT");
                }
            });
        }
    }
}
