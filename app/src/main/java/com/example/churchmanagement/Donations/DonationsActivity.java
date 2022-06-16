package com.example.churchmanagement.Donations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.churchmanagement.Authentication.Login;
import com.example.churchmanagement.Authentication.LoginDB;
import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.Events.EventAdapter;
import com.example.churchmanagement.Events.EventsActivity;
import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.Model.Example;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.ActivityDonationsBinding;
import com.example.churchmanagement.databinding.ActivityMainBinding;
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

public class DonationsActivity extends BaseActivity implements GetResult.MyListener , RecyclerViewClickInterface {

    List<AllDonation> allDonations;
    ActivityDonationsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonationsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("Donations");
        ActionBarUtils.setActionBar(this, true);
        Login loginfo = new LoginDB().getData(Utils.getSharedPreference().getString("username", "st"));
        initViews(binding, loginfo);
    }

    private void initViews(ActivityDonationsBinding binding, Login loginfo) {

        if(Utils.getSharedPreference().getString("username","").equalsIgnoreCase("admin@gmail.com")){

            getAllDonations();
        }

        else{

            startActivity(new Intent(this,PaymentPage.class));
        }
    }

    private void getAllDonations() {

        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("donation", Utils.getSharedPreference().getString("username",""));
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllDonations((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllDonations");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {
        Gson gson = new Gson();
        Example example = gson.fromJson(result.toString(), Example.class);
        allDonations = new ArrayList<>();
        if (example.getResultData().getAllDonations() == null) {
            hideProgressWheel(true);
            binding.rcvDonations.setVisibility(View.GONE);

        } else {
            allDonations = example.getResultData().getAllDonations();
            hideProgressWheel(true);
            binding.rcvDonations.setVisibility(View.VISIBLE);

            DonationAdapter adapter = new DonationAdapter(DonationsActivity.this, allDonations, this);
            binding.rcvDonations.setLayoutManager(new LinearLayoutManager(this));
           binding.rcvDonations.setAdapter(adapter);


        }


    }

    @Override
    public void onItemClick(int position, String chk) {

    }
}