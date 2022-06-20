package com.example.churchmanagement.Booking;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.Events.EventAdapter;
import com.example.churchmanagement.Events.EventsActivity;
import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.Model.Example;
import com.example.churchmanagement.Model.ResponseCommon;
import com.example.churchmanagement.R;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.ActivityBookingBinding;
import com.example.churchmanagement.utils.ActionBarUtils;
import com.example.churchmanagement.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

public class BookingActivity extends BaseActivity implements View.OnClickListener, GetResult.MyListener, RecyclerViewClickInterface {

    ActivityBookingBinding binding;
    private final int CGALLERY = 1;
    String update_id = "", view_type = "general";
    List<AllAmenity> allAmenities;
    List<AllAdminBooking> allAdminBookings;
    String eventencodedImage = "", imgname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("Bookings");
        ActionBarUtils.setActionBar(this, true);
        initViews(binding);
    }

    private void initViews(ActivityBookingBinding binding) {
        binding.btnAdd.setOnClickListener(this);
        binding.btnView.setOnClickListener(this);
        binding.btnAdAttach.setOnClickListener(this);
        binding.btnViewAllBooking.setOnClickListener(this);
        if (Utils.getSharedPreference().getString("username", "").equalsIgnoreCase("admin@gmail.com")) {
            requestPermissions();

        } else {
            binding.addView.setVisibility(View.GONE);
            binding.btnViewAllBooking.setVisibility(View.GONE);
            binding.btnAdd.setVisibility(View.GONE);
            binding.btnView.setText("Go Back");
            getAllAmenities();
        }


    }

    private void requestPermissions() {
        Dexter.withActivity(this)
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == CGALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    binding.imgEvent.setVisibility(View.VISIBLE);
                    binding.imgEvent.setImageBitmap(bitmap);
                    binding.btnAdAttach.setText("Remove");
                    eventencodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    imgname = String.valueOf(Calendar.getInstance().getTimeInMillis());


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_add:

                if (binding.edBookFor.getText().toString().isEmpty() &&
                        binding.edDescription.getText().toString().isEmpty() && eventencodedImage.isEmpty()) {

                    Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();

                } else {

                    addToAmentites();
                }


                break;
                
                
            case R.id.btn_view_all_booking:
                binding.addView.setVisibility(View.GONE);
                getAllAdminBooings();
                
                break;

            case R.id.btn_adAttach:
                if (!binding.btnAdAttach.getText().toString().equalsIgnoreCase("Remove")) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(galleryIntent, CGALLERY);

                } else {
                    binding.btnAdAttach.setText("Attach");
                    binding.imgEvent.setVisibility(View.GONE);
                    eventencodedImage = "";
                    imgname = "";
                }

                break;


            case R.id.btn_view:
                binding.addView.setVisibility(View.GONE);
                getAllAmenities();

                break;


        }

    }

    private void getAllAdminBooings() {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("booking_id", "1");
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllAdminBookings((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllAdminBookings");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAllAmenities() {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("amenities", "1");
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllAmenities((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllAmenities");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addToAmentites() {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("booking_for", binding.edBookFor.getText().toString().trim());
            jsonObject.put("description", binding.edDescription.getText().toString());
            jsonObject.put("image", eventencodedImage);
            jsonObject.put("image_name", imgname);
            jsonObject.put("active", "1");
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().addAmenities((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "addAmenities");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position, String chk) {
        update_id = allAdminBookings.get(position).getId();
        if (chk.equalsIgnoreCase("adconfirm")) {

            showProgressWheel();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username",allAdminBookings.get(position).getUsername());
                jsonObject.put("time",allAdminBookings.get(position).getTimeOfBooking());
                jsonObject.put("status", "1");
                jsonObject.put("id", allAdminBookings.get(position).getId());
                jsonObject.put("amen_id", allAdminBookings.get(position).getAmenId());
                JsonParser jsonParser = new JsonParser();
                Call<JsonObject> call = APIClient.getInterface().updateBStatus((JsonObject) jsonParser.parse(jsonObject.toString()));
                GetResult getResult = new GetResult();
                getResult.setMyListener(this);
                getResult.onNCHandle(call, "updateBStatus");
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("addAmenities")) {
            Gson gson = new Gson();
            ResponseCommon responseCommon = gson.fromJson(result.toString(), ResponseCommon.class);
            if (responseCommon.getResult().equalsIgnoreCase("true")) {
                hideProgressWheel(true);
                showAlert(responseCommon.getResponseMsg(), "Success");
                clearAllFields();
            } else {

                hideProgressWheel(true);
                showAlert(responseCommon.getResponseMsg(), "Error");
            }


        }

        else if(callNo.equalsIgnoreCase("updateBStatus")){
            Gson gson = new Gson();
            ResponseCommon responseCommon = gson.fromJson(result.toString(), ResponseCommon.class);
            if (responseCommon.getResult().equalsIgnoreCase("true")) {
                hideProgressWheel(true);
                showAlert(responseCommon.getResponseMsg(), "Success");
                clearAllFields();
            } else {

                hideProgressWheel(true);
                showAlert(responseCommon.getResponseMsg(), "Error");
            }



        }
        else if(callNo.equalsIgnoreCase("getAllAdminBookings")){

            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            allAdminBookings = new ArrayList<>();
            if (example.getResultData().getAllAdminBooking() == null) {
                hideProgressWheel(true);
                binding.tvnothing.setVisibility(View.VISIBLE);
                binding.rcvAllBookingsAdmin.setVisibility(View.GONE);
                binding.rcvAllBookings.setVisibility(View.GONE);

            } else {
                allAdminBookings = example.getResultData().getAllAdminBooking();
                hideProgressWheel(true);
                binding.rcvAllBookings.setVisibility(View.GONE);
                binding.rcvAllBookingsAdmin.setVisibility(View.VISIBLE);
                AdminBookAdapter adapter = new AdminBookAdapter(BookingActivity.this, allAdminBookings, this);
                binding.rcvAllBookingsAdmin.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvAllBookingsAdmin.setAdapter(adapter);


            }



        }

        else if (callNo.equalsIgnoreCase("getAllAmenities")) {
            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            allAmenities = new ArrayList<>();
            if (example.getResultData().getAllAmenities() == null) {
                hideProgressWheel(true);
                binding.tvnothing.setVisibility(View.VISIBLE);
                binding.rcvAllBookings.setVisibility(View.GONE);
                binding.rcvAllBookingsAdmin.setVisibility(View.GONE);

            } else {
                allAmenities = example.getResultData().getAllAmenities();
                hideProgressWheel(true);
                binding.rcvAllBookings.setVisibility(View.VISIBLE);
                binding.rcvAllBookingsAdmin.setVisibility(View.GONE);
                BookingAdapter adapter = new BookingAdapter(BookingActivity.this, allAmenities, this,view_type);
                binding.rcvAllBookings.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvAllBookings.setAdapter(adapter);


            }

        } else {


        }

    }

    private void clearAllFields() {
        binding.edBookFor.setText("");
        binding.edDescription.setText("");
        binding.imgEvent.setVisibility(View.GONE);
        imgname = "";
        eventencodedImage = "";
    }
}