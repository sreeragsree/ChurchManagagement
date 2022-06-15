package com.example.churchmanagement.Ministry;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.Model.Example;
import com.example.churchmanagement.Model.ResponseCommon;
import com.example.churchmanagement.Model.ResultData;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.databinding.MinistryCardBinding;
import com.example.churchmanagement.databinding.MinistryViewBinding;
import com.example.churchmanagement.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MinistryAdapter extends RecyclerView.Adapter<MinistryAdapter.ViewHolder> {

    Context context;
    List<AllMinistry> ministries = new ArrayList<>();
    RecyclerViewClickInterface recyclerViewClickInterface;

    List<MinistryReqStatusId> ministryStatusList;

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

        //        if not admin
        if (!Utils.getSharedPreference().getString("username", "").equalsIgnoreCase("admin@gmail.com")) {
            holder.binding.adminAddDel.setVisibility(View.GONE);
            holder.binding.btnJoin.setVisibility(View.VISIBLE);

        }else
        {
            holder.binding.btnAlready.setVisibility(View.GONE);
            holder.binding.btnPending.setVisibility(View.GONE);
            holder.binding.btnJoin.setVisibility(View.GONE);
            holder.binding.btnViewmembers.setVisibility(View.VISIBLE);
        }

        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("member_id", Utils.getSharedPreference().getString("username", ""));
            jsonObject.put("ministry_id", ministry.getId());

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getMinistryRequestStatus((JsonObject) jsonParser.parse(jsonObject.toString()));

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    JsonObject object = response.body();

                    Gson gson = new Gson();
                    Example example = gson.fromJson(object.toString(),Example.class);

                    if(example.getResultData().getMinistryReqStatusId() == null)
                    {
                        holder.binding.btnJoin.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        ministryStatusList = example.getResultData().getMinistryReqStatusId();

                        if (ministryStatusList.get(0).getStatus().equalsIgnoreCase("0"))
                        {
                            holder.binding.btnJoin.setVisibility(View.GONE);
                            holder.binding.btnPending.setVisibility(View.VISIBLE);

                        }else if (ministryStatusList.get(0).getStatus().equalsIgnoreCase("1"))
                        {
                            holder.binding.btnAlready.setVisibility(View.VISIBLE);
                            holder.binding.btnJoin.setVisibility(View.GONE);
                        }


                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }
        catch (JSONException e) {
            e.printStackTrace();
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

            binding.btnViewmembers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(), "VIEWMEMBERS");
                }
            });

        }
    }
}
