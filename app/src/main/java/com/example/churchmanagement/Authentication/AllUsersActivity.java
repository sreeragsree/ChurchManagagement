package com.example.churchmanagement.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.Model.Example;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.ActivityAllUsersBinding;
import com.example.churchmanagement.databinding.ActivityDonationsBinding;
import com.example.churchmanagement.utils.ActionBarUtils;
import com.example.churchmanagement.utils.RecyclerViewClickInterface;
import com.example.churchmanagement.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AllUsersActivity extends BaseActivity implements GetResult.MyListener, RecyclerViewClickInterface {

    ActivityAllUsersBinding binding;
    List<AllMember> allMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllUsersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("All Users");
        ActionBarUtils.setActionBar(this, true);
        Login loginfo = new LoginDB().getData(Utils.getSharedPreference().getString("username", "st"));
        initViews(binding, loginfo);
    }

    private void initViews(ActivityAllUsersBinding binding, Login loginfo) {
        getAllUsers();
    }

    private void getAllUsers() {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("members","0");
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllMembers((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllMembers");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("getAllMembers")){
            hideProgressWheel(true);
            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            allMembers = new ArrayList<>();
            if (example.getResultData().getAllMembers() != null) {
                allMembers.addAll(example.getResultData().getAllMembers());
                AllMemberAdapter adapter = new AllMemberAdapter(this, allMembers, this);
                binding.rcvMem.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvMem.setAdapter(adapter);

            }


        }


    }

    @Override
    public void onItemClick(int position, String chk) {

    }
}