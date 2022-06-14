package com.example.churchmanagement.Ministry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.churchmanagement.Authentication.Login;
import com.example.churchmanagement.Authentication.LoginDB;
import com.example.churchmanagement.R;
import com.example.churchmanagement.databinding.ActivityDonationsBinding;
import com.example.churchmanagement.databinding.ActivityLoginBinding;
import com.example.churchmanagement.databinding.ActivityMinistryBinding;
import com.example.churchmanagement.utils.ActionBarUtils;
import com.example.churchmanagement.utils.Utils;

public class MinistryActivity extends AppCompatActivity {
    
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

    private void initViews(ActivityMinistryBinding binding, Login loginfo) {
    }
}