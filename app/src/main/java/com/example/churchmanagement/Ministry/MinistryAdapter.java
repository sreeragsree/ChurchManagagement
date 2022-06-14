package com.example.churchmanagement.Ministry;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.churchmanagement.databinding.MinistryViewBinding;

public class MinistryAdapter extends RecyclerView.Adapter<MinistryAdapter.ViewHolder> {
    @NonNull
    @Override
    public MinistryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MinistryAdapter.ViewHolder(MinistryViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MinistryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MinistryViewBinding binding;
        public ViewHolder(@NonNull MinistryViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
