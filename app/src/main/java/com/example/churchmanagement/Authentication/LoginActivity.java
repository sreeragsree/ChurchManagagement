package com.example.churchmanagement.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.MainActivity;
import com.example.churchmanagement.Model.ResponseCommon;
import com.example.churchmanagement.R;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.ActivityLoginBinding;
import com.example.churchmanagement.databinding.ActivityRegisterBinding;
import com.example.churchmanagement.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class LoginActivity extends BaseActivity implements View.OnClickListener,GetResult.MyListener {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Utils.setSystemBarColor(this, R.color.indigo_50);
        Utils.setSystemBarLight(this);
        initViews(binding);
    }

    private void initViews(ActivityLoginBinding binding) {
        if (!Utils.getSharedPreference().getString("username", "").isEmpty()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        binding.btnLogin.setOnClickListener(this);
        binding.create.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.create:
                startActivity(new Intent(this,RegisterActivity.class));

                break;


            case R.id.btn_login:

                if(binding.inputPhone.getText().toString().isEmpty() &&
                        binding.inputPassword.getText().toString().isEmpty()){

                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                else{
                    showProgressWheel();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username", binding.inputPhone.getText().toString().trim());
                        jsonObject.put("password", binding.inputPassword.getText().toString().trim());

                        JsonParser jsonParser = new JsonParser();
                        Call<JsonObject> call = APIClient.getInterface().login((JsonObject) jsonParser.parse(jsonObject.toString()));
                        GetResult getResult = new GetResult();
                        getResult.setMyListener(this);
                        getResult.onNCHandle(call, "login");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


                break;


        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("login")){
            hideProgressWheel(true);
            Gson gson = new Gson();
            ResponseCommon responseCommon = gson.fromJson(result.toString(), ResponseCommon.class);

            if(responseCommon.getResult().equalsIgnoreCase("true")){
                Utils.getSharedPreferenceEdit().putString("username", binding.inputPhone.getText().toString());
                Utils.getSharedPreferenceEdit().apply();
                startActivity(new Intent(this, MainActivity.class));

            }
            else{

                Toast.makeText(this, responseCommon.getResponseMsg(), Toast.LENGTH_SHORT).show();
            }


        }

    }
}