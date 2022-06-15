package com.example.churchmanagement.Donations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.churchmanagement.Authentication.Login;
import com.example.churchmanagement.Authentication.LoginDB;
import com.example.churchmanagement.R;
import com.example.churchmanagement.databinding.ActivityDonationsBinding;
import com.example.churchmanagement.databinding.ActivityPaymentPageBinding;
import com.example.churchmanagement.utils.ActionBarUtils;
import com.example.churchmanagement.utils.Utils;

public class PaymentPage extends AppCompatActivity {

    ActivityPaymentPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment_page);

        binding = ActivityPaymentPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("Donations");
        ActionBarUtils.setActionBar(this, true);
        Login loginfo = new LoginDB().getData(Utils.getSharedPreference().getString("username", "st"));
        initViews(binding, loginfo);
    }

    private void initViews(ActivityPaymentPageBinding binding, Login loginfo) {
    }
}