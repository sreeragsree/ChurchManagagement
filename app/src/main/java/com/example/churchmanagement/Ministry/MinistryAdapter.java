package com.example.churchmanagement.Ministry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.databinding.MinistryCardBinding;
import com.example.churchmanagement.databinding.MinistryViewBinding;
import com.example.churchmanagement.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MinistryAdapter extends RecyclerView.Adapter<MinistryAdapter.ViewHolder> {

    Context context;
    List<AllMinistry> ministries = new ArrayList<>();
    RecyclerViewClickInterface recyclerViewClickInterface;

    public MinistryAdapter(MinistryActivity ministryActivity, List<AllMinistry> ministrylist, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = ministryActivity;
        this.ministries = ministrylist;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public MinistryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MinistryAdapter.ViewHolder(MinistryCardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MinistryAdapter.ViewHolder holder, int position) {

        AllMinistry ministry = ministries.get(position);
        holder.binding.MinistryName.setText(ministry.getMinistryName());
        holder.binding.tvMinistryCategory.setText(ministry.getMinistryCategory());
        holder.binding.tvNoOfMembers.setText(ministry.getMemberLimit());
        holder.binding.tvSpecification.setText(ministry.getDescription());
        if (!Utils.getSharedPreference().getString("username", "").equalsIgnoreCase("admin@gmail.com")) {
            holder.binding.adminAddDel.setVisibility(View.GONE);
            holder.binding.btnJoin.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return ministries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MinistryCardBinding binding;

        public ViewHolder(@NonNull MinistryCardBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            binding.ivEditministry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(), "EDIT");
                }
            });

            binding.btnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(), "JOIN");
                }
            });

            binding.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(), "DELETE");
                }
            });

        }
    }
}
