package com.example.churchmanagement.Ministry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.churchmanagement.Authentication.Login;
import com.example.churchmanagement.Authentication.LoginDB;
import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.Model.ResponseCommon;
import com.example.churchmanagement.R;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.ActivityDonationsBinding;
import com.example.churchmanagement.databinding.ActivityLoginBinding;
import com.example.churchmanagement.databinding.ActivityMinistryBinding;
import com.example.churchmanagement.utils.ActionBarUtils;
import com.example.churchmanagement.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class MinistryActivity extends BaseActivity implements GetResult.MyListener,View.OnClickListener {
    
    ActivityMinistryBinding binding;

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
        members = binding.edMembers.getText().toString();
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
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        }

    }

    private void initViews(ActivityMinistryBinding binding, Login loginfo) {

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
                showProgressWheel();
                addMinistry();
                break;


        }
    }
}