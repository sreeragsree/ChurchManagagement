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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initViews(binding);
    }

    private void initViews(ActivityLoginBinding binding) {
        binding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_login:

                if(binding.inputPhone.getText().toString().isEmpty() &&
                        binding.inputPassword.getText().toString().isEmpty()){

                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                else if(!new LoginDB().login(binding.inputPhone.getText().toString(),binding.inputPassword.getText().toString())){

                    Toast.makeText(this, "Invalid username / password", Toast.LENGTH_SHORT).show();
                }

                else{

                    startActivity(new Intent(this, MainActivity.class));
                }

                break;


        }

    }
}