package com.example.churchmanagement.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.churchmanagement.R;
import com.example.churchmanagement.databinding.ActivityRegisterBinding;
import com.example.churchmanagement.utils.Utils;

import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    ActivityRegisterBinding binding;
    private Realm realm;
    Boolean s=true;

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
        switch (view.getId()){

            case R.id.btn_login:

                startActivity(new Intent(this,LoginActivity.class));
                break;

            case R.id.btn_signup:


                if(binding.inputName.getText().toString().isEmpty() &&
                        binding.inputEmail.getText().toString().isEmpty() &&
                        binding.inputPhonenumber.getText().toString().isEmpty() &&
                        binding.inputPassword.getText().toString().isEmpty()){

                    Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();

                }

                else if(new LoginDB().checkUsernameExist( binding.inputEmail.getText().toString())){

                    Toast.makeText(this, "Email already exist", Toast.LENGTH_SHORT).show();

                }

                else{
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


                break;
        }

    }
}