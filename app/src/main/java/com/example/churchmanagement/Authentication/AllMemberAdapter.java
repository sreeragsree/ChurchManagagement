package com.example.churchmanagement.Authentication;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.churchmanagement.databinding.AllmemberViewBinding;

public class AllMemberAdapter extends RecyclerView.Adapter<AllMemberAdapter.ViewHolder> {


    @NonNull
    @Override
    public AllMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AllmemberViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllMemberAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AllmemberViewBinding rowXmlViewBinding;
        public ViewHolder(@NonNull AllmemberViewBinding rowXmlViewBinding) {
            super(rowXmlViewBinding.getRoot());
            this.rowXmlViewBinding = rowXmlViewBinding;
        }
    }
}
