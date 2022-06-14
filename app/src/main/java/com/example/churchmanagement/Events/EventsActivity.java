package com.example.churchmanagement.Events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.databinding.ActivityEventsBinding;
import com.example.churchmanagement.databinding.ActivityMainBinding;
import com.example.churchmanagement.utils.ActionBarUtils;

public class EventsActivity extends BaseActivity {

    ActivityEventsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEventsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("Events");
        ActionBarUtils.setActionBar(this, true);
        initViews(binding);
    }

    private void initViews(ActivityEventsBinding binding) {
    }
}