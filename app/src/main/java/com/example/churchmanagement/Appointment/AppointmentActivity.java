package com.example.churchmanagement.Appointment;

import android.os.Bundle;
import android.view.View;

import com.example.churchmanagement.Authentication.Login;
import com.example.churchmanagement.Authentication.LoginDB;
import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.databinding.ActivityAppointmentBinding;
import com.example.churchmanagement.utils.ActionBarUtils;
import com.example.churchmanagement.utils.Utils;

public class AppointmentActivity extends BaseActivity {
    
    ActivityAppointmentBinding binding;

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
    }
}