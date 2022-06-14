package com.example.churchmanagement.Advertisement;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.churchmanagement.Authentication.Login;
import com.example.churchmanagement.Authentication.LoginDB;
import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.databinding.ActivityAdvertisementBinding;
import com.example.churchmanagement.utils.ActionBarUtils;
import com.example.churchmanagement.utils.Utils;

public class AdvertisementActivity extends BaseActivity {

    ActivityAdvertisementBinding binding;
    Login loginfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdvertisementBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("Advertisement");
        ActionBarUtils.setActionBar(this, true);
         loginfo = new LoginDB().getData(Utils.getSharedPreference().getString("username", "st"));
        initViews(binding, loginfo);
    }

    private void initViews(ActivityAdvertisementBinding binding, Login loginfo) {
        Toast.makeText(this,   loginfo.getRole(), Toast.LENGTH_SHORT).show();

    }
}