package com.example.churchmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.churchmanagement.Advertisement.AdvertisementActivity;
import com.example.churchmanagement.Appointment.AppointmentActivity;
import com.example.churchmanagement.Authentication.AllUsersActivity;
import com.example.churchmanagement.Authentication.Login;
import com.example.churchmanagement.Authentication.LoginActivity;
import com.example.churchmanagement.Authentication.LoginDB;
import com.example.churchmanagement.Booking.BookingActivity;
import com.example.churchmanagement.Donations.DonationsActivity;
import com.example.churchmanagement.Events.EventsActivity;
import com.example.churchmanagement.Ministry.MinistryActivity;
import com.example.churchmanagement.News.NewsActivity;
import com.example.churchmanagement.databinding.ActivityMainBinding;
import com.example.churchmanagement.utils.ActionBarUtils;
import com.example.churchmanagement.utils.Utils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("Home");
        ActionBarUtils.setActionBar(this, false);
        Login loginfo = new LoginDB().getData(Utils.getSharedPreference().getString("username", "st"));
        initViews(binding, loginfo);
        checkDeepLink();
    }

    private void checkDeepLink() {
        if (getIntent() != null && getIntent().getData() != null) {
            Uri data = getIntent().getData();

            Toast.makeText(this, data.getQueryParameter("securityCode"), Toast.LENGTH_SHORT).show();

        }
    }

    private void initViews(ActivityMainBinding binding, Login loginfo) {
        binding.tbMain.logout.setVisibility(View.VISIBLE);
        binding.masterData.setOnClickListener(this);
        binding.tbMain.logout.setOnClickListener(this);
        binding.members.setOnClickListener(this);
        binding.advertisement.setOnClickListener(this);
        binding.appointment.setOnClickListener(this);
        binding.donations.setOnClickListener(this);
        binding.bookings.setOnClickListener(this);
        binding.news.setOnClickListener(this);
        binding.events.setOnClickListener(this);


        if (!Utils.getSharedPreference().getString("username", "").equalsIgnoreCase("admin@gmail.com")) {
            binding.members.setVisibility(View.GONE);
            binding.advertisement.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.logout:
                Utils.getSharedPreferenceEdit().putString("username", "");
                Utils.getSharedPreferenceEdit().apply();
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();

                break;

            case R.id.members:
                startActivity(new Intent(this, AllUsersActivity.class));
                break;

            case R.id.advertisement:
                startActivity(new Intent(this, AdvertisementActivity.class).putExtra("req_type","advertisement"));
                break;

            case R.id.events:
                startActivity(new Intent(this, EventsActivity.class));
                break;

            case R.id.appointment:
                startActivity(new Intent(this, AppointmentActivity.class));
                break;

            case R.id.news:
                startActivity(new Intent(this, AdvertisementActivity.class).putExtra("req_type","news"));
                break;

            case R.id.bookings:
                startActivity(new Intent(this, BookingActivity.class));
                break;

            case R.id.master_data:
                startActivity(new Intent(this, MinistryActivity.class));
                break;
            case R.id.donations:
                startActivity(new Intent(this, DonationsActivity.class));
                break;


        }


    }
}