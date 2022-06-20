package com.example.churchmanagement.Authentication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.churchmanagement.R;
import com.example.churchmanagement.databinding.AllmemberViewBinding;
import com.example.churchmanagement.utils.RecyclerViewClickInterface;

import java.util.List;

public class AllMemberAdapter extends RecyclerView.Adapter<AllMemberAdapter.ViewHolder> {

    Context context;
    List<AllMember> allMembers;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public AllMemberAdapter(Context context, List<AllMember> allMembers, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.allMembers = allMembers;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public AllMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(AllmemberViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllMemberAdapter.ViewHolder holder, int position) {
        AllMember member = allMembers.get(position);
        holder.rowXmlViewBinding.memName.setText("Name : " + member.getName());
        holder.rowXmlViewBinding.memEmail.setText("Email : " + member.getEmail());
        holder.rowXmlViewBinding.memPhone.setText("Mobile : " + member.getMobile());
        if (member.getStatus().equalsIgnoreCase("0")) {

            holder.rowXmlViewBinding.status.setText("ACTIVE");
            holder.rowXmlViewBinding.status.setBackgroundColor(context.getResources().getColor(R.color.red));

        } else {

            holder.rowXmlViewBinding.status.setText("DE-ACTIVATE");
            holder.rowXmlViewBinding.status.setBackgroundColor(context.getResources().getColor(R.color.green));
        }

    }

    @Override
    public int getItemCount() {
        return allMembers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AllmemberViewBinding rowXmlViewBinding;

        public ViewHolder(@NonNull AllmemberViewBinding rowXmlViewBinding) {
            super(rowXmlViewBinding.getRoot());
            this.rowXmlViewBinding = rowXmlViewBinding;

            rowXmlViewBinding.cardFull.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(), "allmember");
                }
            });

            rowXmlViewBinding.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(), "STATUS");
                }
            });




        }
    }
}
