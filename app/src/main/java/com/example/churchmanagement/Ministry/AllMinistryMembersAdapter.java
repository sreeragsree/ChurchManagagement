package com.example.churchmanagement.Ministry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.databinding.AllministryMemberscardBinding;
import com.example.churchmanagement.databinding.MinistryCardBinding;

import java.util.List;

public class AllMinistryMembersAdapter extends RecyclerView.Adapter<AllMinistryMembersAdapter.ViewHolder> {

    Context context;
    List<MemberListMinistry> memberListMinistries;
    RecyclerViewClickInterface recyclerViewClickInterface;

    public AllMinistryMembersAdapter(MinistryActivity ministryActivity, List<MemberListMinistry> ministryMembers, MinistryActivity ministryActivity1) {
        this.context = ministryActivity;
        this.memberListMinistries = ministryMembers;
        this.recyclerViewClickInterface = ministryActivity1;
    }

    @NonNull
    @Override
    public AllMinistryMembersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(AllministryMemberscardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AllMinistryMembersAdapter.ViewHolder holder, int position) {

        MemberListMinistry memberListMinistry = memberListMinistries.get(position);

        holder.binding.memberName.setText(memberListMinistry.getMemberId());

        if(memberListMinistry.getStatus().equalsIgnoreCase("0"))
        {
            holder.binding.ivAccept.setVisibility(View.VISIBLE);
            holder.binding.ivDecline.setVisibility(View.VISIBLE);
        }
        else if(memberListMinistry.getStatus().equalsIgnoreCase("1"))
        {
            holder.binding.ivDecline.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return memberListMinistries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AllministryMemberscardBinding binding;

        public ViewHolder(@NonNull AllministryMemberscardBinding binding) {

            super(binding.getRoot());

            this.binding = binding;

            binding.ivAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(), "ACCEPT");
                }
            });

            binding.ivDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(), "REJECT");
                }
            });
        }
    }
}
