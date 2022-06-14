package com.example.churchmanagement.Ministry;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.churchmanagement.Authentication.Login;
import com.example.churchmanagement.Authentication.LoginDB;
import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.Model.Example;
import com.example.churchmanagement.Model.ResponseCommon;
import com.example.churchmanagement.Model.ResultData;
import com.example.churchmanagement.R;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.ActivityMinistryBinding;
import com.example.churchmanagement.utils.ActionBarUtils;
import com.example.churchmanagement.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MinistryActivity extends BaseActivity implements GetResult.MyListener,View.OnClickListener, RecyclerViewClickInterface {
    
    ActivityMinistryBinding binding;
    List<AllMinistry> ministrylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMinistryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("Ministry");
        ActionBarUtils.setActionBar(this, true);
        Login loginfo = new LoginDB().getData(Utils.getSharedPreference().getString("username", "st"));
        initViews(binding, loginfo);

    }

    private void addMinistry() {

        String name,category,members,spec;

        name = binding.edName.getText().toString();
        category = binding.edCategory.getText().toString();
        members = binding.edNoofpersons.getText().toString();
        spec = binding.edSpec.getText().toString();

        if( !name.equals("") && !category.equals("") && !members.equals("") && !spec.equals(""))
        {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ministry_name", name);
                jsonObject.put("ministry_category", category);
                jsonObject.put("member_limit", members);
                jsonObject.put("description", spec);

                JsonParser jsonParser = new JsonParser();
                Call<JsonObject> call = APIClient.getInterface().addMinistry((JsonObject) jsonParser.parse(jsonObject.toString()));
                GetResult getResult = new GetResult();
                getResult.setMyListener(this);
                getResult.onNCHandle(call, "addMinistry");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            hideProgressWheel(true);
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        }

    }

    private void initViews(ActivityMinistryBinding binding, Login loginfo) {

        binding.btnAdd.setOnClickListener(this);
        binding.btnView.setOnClickListener(this);

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("addMinistry")) {

            Gson gson = new Gson();
            hideProgressWheel(true);

            ResponseCommon example = gson.fromJson(result.toString(), ResponseCommon.class);

            if(example.getResult().equalsIgnoreCase("true"))
            {
                showAlert("Ministry added successfully","Success");
                clearFileds();
            }
            else
            {
                Toast.makeText(MinistryActivity.this, example.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }
        }
        else if(callNo.equalsIgnoreCase("getAllMinistry")) {

            Gson gson = new Gson();
            hideProgressWheel(true);

            Example example = gson.fromJson(result.toString(),Example.class);

            ministrylist = new ArrayList<>();




            if(example.getResultData().getAllMinistry() == null)
            {
                hideProgressWheel(true);
                binding.tvnothing.setVisibility(View.VISIBLE);
                binding.rcvAllministries.setVisibility(View.GONE);

            }else
            {
                ministrylist = example.getResultData().getAllMinistry();

                hideProgressWheel(true);
                binding.rcvAllministries.setVisibility(View.VISIBLE);

                MinistryAdapter adapter = new MinistryAdapter(MinistryActivity.this, ministrylist,this);
                binding.rcvAllministries.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvAllministries.setAdapter(adapter);

            }

        }
        else  if(callNo.equalsIgnoreCase("updateMinistry")) {

            Gson gson = new Gson();
            hideProgressWheel(true);

            ResponseCommon example = gson.fromJson(result.toString(), ResponseCommon.class);

            if(example.getResult().equalsIgnoreCase("true"))
            {
                showAlert("Ministry updated successfully","Success");
                binding.btnAdd.setText("Add Ministry");
                clearFileds();
            }
            else
            {
                Toast.makeText(MinistryActivity.this, example.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }
        }

        else  if(callNo.equalsIgnoreCase("del_ministry")) {

            Gson gson = new Gson();
            hideProgressWheel(true);

            ResponseCommon example = gson.fromJson(result.toString(), ResponseCommon.class);

            if(example.getResult().equalsIgnoreCase("true"))
            {
                showAlert("Ministry deleted successfully","Success");
                binding.btnAdd.setText("Add Ministry");
                binding.llAddMinistry.setVisibility(View.VISIBLE);
                binding.btnView.setVisibility(View.VISIBLE);
                binding.rcvAllministries.setVisibility(View.GONE);
                binding.tvnothing.setVisibility(View.GONE);
            }
            else
            {
                Toast.makeText(MinistryActivity.this, example.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }
        }




    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_add:

                if(binding.btnAdd.getText().toString().equalsIgnoreCase("Add Ministry"))
                {
                    showProgressWheel();
                    addMinistry();
                }
                else if(binding.btnAdd.getText().toString().equalsIgnoreCase("Go Back"))
                {
                    binding.btnAdd.setText("Add Ministry");
                    binding.llAddMinistry.setVisibility(View.VISIBLE);
                    binding.btnView.setVisibility(View.VISIBLE);
                    binding.rcvAllministries.setVisibility(View.GONE);
                    binding.tvnothing.setVisibility(View.GONE);
                }
                else if(binding.btnAdd.getText().toString().equalsIgnoreCase("Update Ministry"))
                {
                    showProgressWheel();
                    updateMinistry();
                }



                break;

            case R.id.btn_view:
                binding.btnAdd.setText("Go Back");
                binding.btnView.setVisibility(View.GONE);
                binding.llAddMinistry.setVisibility(View.GONE);
                showProgressWheel();
                getAllMinistries();
                break;


        }
    }

    private void updateMinistry() {

        String name,category,members,spec,id;

        id = binding.edId.getText().toString();
        name = binding.edName.getText().toString();
        category = binding.edCategory.getText().toString();
        members = binding.edNoofpersons.getText().toString();
        spec = binding.edSpec.getText().toString();

        if( !name.equals("") && !category.equals("") && !members.equals("") && !spec.equals(""))
        {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", id);
                jsonObject.put("ministry_name", name);
                jsonObject.put("ministry_category", category);
                jsonObject.put("member_limit", members);
                jsonObject.put("description", spec);

                JsonParser jsonParser = new JsonParser();
                Call<JsonObject> call = APIClient.getInterface().updateMinistry((JsonObject) jsonParser.parse(jsonObject.toString()));
                GetResult getResult = new GetResult();
                getResult.setMyListener(this);
                getResult.onNCHandle(call, "updateMinistry");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            hideProgressWheel(true);
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        }

    }

    private void getAllMinistries() {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("ministry", "1");

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllMinistry((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllMinistry");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position, String chk) {

        if (chk.equalsIgnoreCase("EDIT")) {

            String id = ministrylist.get(position).getId();

            binding.edId.setText(id);
            binding.edName.setText(ministrylist.get(position).getMinistryName());
            binding.edCategory.setText(ministrylist.get(position).getMinistryCategory());
            binding.edNoofpersons.setText(ministrylist.get(position).getMemberLimit());
            binding.edSpec.setText(ministrylist.get(position).getDescription());

            binding.btnAdd.setText("Update Ministry");
            binding.llAddMinistry.setVisibility(View.VISIBLE);
            binding.btnView.setVisibility(View.VISIBLE);
            binding.rcvAllministries.setVisibility(View.GONE);
            binding.tvnothing.setVisibility(View.GONE);

        }
        else if(chk.equalsIgnoreCase("DELETE"))
        {
            String id = ministrylist.get(position).getId();

            deleteMinistry(id);
        }


    }

    private void deleteMinistry(String id) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("id", id);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().del_ministry((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "del_ministry");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void clearFileds()
    {
        binding.edName.setText("");
        binding.edSpec.setText("");
        binding.edNoofpersons.setText("");
        binding.edCategory.setText("");
        binding.edId.setText("");
    }
}