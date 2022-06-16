package com.example.churchmanagement.Appointment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.churchmanagement.Authentication.Login;
import com.example.churchmanagement.Authentication.LoginDB;
import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.Model.Example;
import com.example.churchmanagement.Model.ResponseCommon;
import com.example.churchmanagement.R;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.ActivityAppointmentBinding;
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

public class AppointmentActivity extends BaseActivity implements View.OnClickListener, GetResult.MyListener, RecyclerViewClickInterface {

    ActivityAppointmentBinding binding;
    List<AllAppointment> allAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppointmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("Appointment");
        ActionBarUtils.setActionBar(this, true);
        Login loginfo = new LoginDB().getData(Utils.getSharedPreference().getString("username", "st"));
        initViews(binding, loginfo);
    }

    private void initViews(ActivityAppointmentBinding binding, Login loginfo) {
        binding.btnAdd.setOnClickListener(this);
        binding.btnView.setOnClickListener(this);
        binding.edApoDate.setOnClickListener(this);
        binding.edApoTime.setOnClickListener(this);

        if (Utils.getSharedPreference().getString("username", "").equalsIgnoreCase("admin@gmail.com")) {

            binding.btnView.setVisibility(View.GONE);
            binding.addView.setVisibility(View.GONE);
            binding.btnAdd.setVisibility(View.GONE);
            getAllAppointments();

        }

    }

    private void getAllAppointments() {

        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            if (Utils.getSharedPreference().getString("username", "").equalsIgnoreCase("admin@gmail.com")) {
                jsonObject.put("appointments", "0");

            } else {

                jsonObject.put("appointments", Utils.getSharedPreference().getString("username", "").toString());

            }

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllAppointments((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllAppointments");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_add:
                if (binding.edApoTime.getText().toString().isEmpty() &&
                        binding.edApoDate.getText().toString().isEmpty() &&
                        binding.edApoReason.getText().toString().isEmpty()) {

                    Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                } else {

                    addAppointment();
                }

                break;

            case R.id.btn_view:
if(binding.btnView.getText().equals("Go Back")){
    binding.btnView.setText("View All");
    binding.rcvAllAppointments.setVisibility(View.GONE);
    binding.addView.setVisibility(View.VISIBLE);
    binding.btnAdd.setVisibility(View.VISIBLE);

}
else{

    binding.btnView.setText("Go Back");
    binding.addView.setVisibility(View.GONE);
    binding.btnAdd.setVisibility(View.GONE);
    getAllAppointments();

}


                break;

            case R.id.ed_ApoDate:


                break;

            case R.id.ed_ApoTime:


                break;

        }


    }

    private void addAppointment() {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appoint_from", Utils.getSharedPreference().getString("username", ""));
            jsonObject.put("reason", binding.edApoReason.getText().toString());
            jsonObject.put("ap_date", binding.edApoDate.getText().toString());
            jsonObject.put("ap_time", binding.edApoTime.getText().toString());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().addAppointment((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "addAppointment");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("addAppointment")) {
            Gson gson = new Gson();
            ResponseCommon responseCommon = gson.fromJson(result.toString(), ResponseCommon.class);
            if (responseCommon.getResult().equalsIgnoreCase("true")) {
                hideProgressWheel(true);
                showAlert(responseCommon.getResponseMsg(), "Success");
                clearAllFields();
            } else {

                hideProgressWheel(true);
                showAlert(responseCommon.getResponseMsg(), "Error");
            }


        } else if (callNo.equalsIgnoreCase("getAllAppointments")) {

            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            allAppointments=new ArrayList<>();
            if (example.getResultData().getAllAppointments() == null) {
                hideProgressWheel(true);
                binding.tvnothing.setVisibility(View.VISIBLE);
                binding.rcvAllAppointments.setVisibility(View.GONE);

            } else {
                allAppointments = example.getResultData().getAllAppointments();
                hideProgressWheel(true);
                binding.rcvAllAppointments.setVisibility(View.VISIBLE);
                AppointmentAdapter adapter = new AppointmentAdapter(AppointmentActivity.this, allAppointments, this);
                binding.rcvAllAppointments.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvAllAppointments.setAdapter(adapter);


            }


        } else {
            Gson gson = new Gson();
            ResponseCommon responseCommon = gson.fromJson(result.toString(), ResponseCommon.class);
            if (responseCommon.getResult().equalsIgnoreCase("true")) {
                hideProgressWheel(true);
                showAlert(responseCommon.getResponseMsg(), "Success");
            } else {

                hideProgressWheel(true);
                showAlert(responseCommon.getResponseMsg(), "Error");
            }


        }

    }

    private void clearAllFields() {
        binding.edApoDate.setText("");
        binding.edApoTime.setText("");
        binding.edApoReason.setText("");
        binding.edApoName.setText("");


    }

    @Override
    public void onItemClick(int position, String chk) {


        if (chk.equalsIgnoreCase("STUP")) {

            String id = allAppointments.get(position).getId();
            showProgressWheel();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", id);
                JsonParser jsonParser = new JsonParser();
                Call<JsonObject> call = APIClient.getInterface().updateAppointmenmtStatus((JsonObject) jsonParser.parse(jsonObject.toString()));
                GetResult getResult = new GetResult();
                getResult.setMyListener(this);
                getResult.onNCHandle(call, "updateAppointmenmtStatus");
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }
}