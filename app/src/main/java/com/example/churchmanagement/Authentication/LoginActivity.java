package com.example.churchmanagement.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.churchmanagement.MainActivity;
import com.example.churchmanagement.R;
import com.example.churchmanagement.databinding.ActivityLoginBinding;
import com.example.churchmanagement.databinding.ActivityRegisterBinding;
import com.example.churchmanagement.utils.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Utils.setSystemBarColor(this, R.color.purple_500);
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

                else if(!new LoginDB().login(binding.inputPhone.getText().toString(),binding.inputPassword.getText().toString())){

                    Toast.makeText(this, "Invalid username / password", Toast.LENGTH_SHORT).show();
                }

                else{
                    Utils.getSharedPreferenceEdit().putString("username", binding.inputPhone.getText().toString());
                    Utils.getSharedPreferenceEdit().apply();
                    startActivity(new Intent(this, MainActivity.class));
                }

                break;


        }

    }
}