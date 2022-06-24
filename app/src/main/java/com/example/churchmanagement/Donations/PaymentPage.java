package com.example.churchmanagement.Donations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.churchmanagement.Authentication.Login;
import com.example.churchmanagement.Authentication.LoginDB;
import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.MainActivity;
import com.example.churchmanagement.Model.ResponseCommon;
import com.example.churchmanagement.R;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.ActivityDonationsBinding;
import com.example.churchmanagement.databinding.ActivityPaymentPageBinding;
import com.example.churchmanagement.utils.ActionBarUtils;
import com.example.churchmanagement.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

public class PaymentPage extends BaseActivity implements View.OnClickListener, GetResult.MyListener {

    ActivityPaymentPageBinding binding;
    ArrayList<String> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("Donations");
        arrayList.add("ORPHANAGE");
        arrayList.add("SCHOOL FUND");
        arrayList.add("FOOD");
        arrayList.add("CHURCH FEST");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.choosePurpose.setAdapter(arrayAdapter);
        ActionBarUtils.setActionBar(this, true);
        initViews(binding);
    }

    private void initViews(ActivityPaymentPageBinding binding) {
        binding.btnPay.setOnClickListener(this);
        binding.trackBookings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.track_bookings:

                startActivity(new Intent(this, MainActivity.class));
                finish();

                break;

            case R.id.btn_pay:

                if(binding.choosePurpose.getSelectedItem().equals("")){


                    Toast.makeText(this, "Please Choose a Purpose", Toast.LENGTH_SHORT).show();
                }
                else if(binding.amount.getText().toString().isEmpty() && binding.amount.getText().toString().equalsIgnoreCase("0") ){

                    Toast.makeText(this, "Please enter an amount or Enter amount > 0", Toast.LENGTH_SHORT).show();
                }
                else{

             addDonation();

                }


                break;

        }

    }

    private void addDonation() {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("payee_id", Utils.getSharedPreference().getString("username",""));
            jsonObject.put("t_id", "txnjyttewuyuuy");
            jsonObject.put("amount_rec", binding.amount.getText().toString().trim());
            jsonObject.put("amount_for", binding.choosePurpose.getSelectedItem().toString());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().addDonations((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "addDonations");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("addDonations")){
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


        }

    }

    private void clearAllFields() {
        binding.lvlone.setVisibility(View.GONE);
        binding.lvltwo.setVisibility(View.VISIBLE);
        binding.amount.setText("0");
    }
}