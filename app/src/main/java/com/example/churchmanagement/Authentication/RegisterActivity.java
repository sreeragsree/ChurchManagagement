package com.example.churchmanagement.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.churchmanagement.Model.ResponseCommon;
import com.example.churchmanagement.R;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.ActivityRegisterBinding;
import com.example.churchmanagement.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, GetResult.MyListener {

    ActivityRegisterBinding binding;
    private Realm realm;
    Boolean s = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Utils.setSystemBarColor(this, R.color.purple_500);
        Utils.setSystemBarLight(this);
        initViews(binding);
    }

    private void initViews(ActivityRegisterBinding binding) {
        binding.btnSignup.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_login:

                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.btn_signup:


                if (binding.inputName.getText().toString().isEmpty() &&
                        binding.inputEmail.getText().toString().isEmpty() &&
                        binding.inputPhonenumber.getText().toString().isEmpty() &&
                        binding.inputPassword.getText().toString().isEmpty()) {

                    Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();

                } else if (new LoginDB().checkUsernameExist(binding.inputEmail.getText().toString())) {

                    Toast.makeText(this, "Email already exist", Toast.LENGTH_SHORT).show();

                } else {


                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", binding.inputName.getText().toString().trim());
                        jsonObject.put("email", binding.inputEmail.getText().toString().trim());
                        jsonObject.put("password", binding.inputPassword.getText().toString().trim());
                        jsonObject.put("mobile", binding.inputPhonenumber.getText().toString().trim());
                        jsonObject.put("role", "user");

                        JsonParser jsonParser = new JsonParser();
                        Call<JsonObject> call = APIClient.getInterface().register((JsonObject) jsonParser.parse(jsonObject.toString()));
                        GetResult getResult = new GetResult();
                        getResult.setMyListener(this);
                        getResult.onNCHandle(call, "register");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                break;
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("register")) {
            Gson gson = new Gson();
            ResponseCommon rs = gson.fromJson(result.toString(), ResponseCommon.class);
            if(rs.getResult().equalsIgnoreCase("true")){
                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Login login = realm.createObject(Login.class);
                login.setUsername(binding.inputName.getText().toString());
                login.setEmail(binding.inputEmail.getText().toString());
                login.setMobile(binding.inputPhonenumber.getText().toString());
                login.setPassword(binding.inputPassword.getText().toString());
                login.setRole("user");
                realm.commitTransaction();
                realm.close();
                      Toast.makeText(this, "Registration successfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,LoginActivity.class));

            }

        }

    }
}