package com.example.churchmanagement.Advertisement;

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

import com.example.churchmanagement.Authentication.Login;
import com.example.churchmanagement.Authentication.LoginDB;
import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.Events.EventAdapter;
import com.example.churchmanagement.Events.EventsActivity;
import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.Model.Example;
import com.example.churchmanagement.Model.ResponseCommon;
import com.example.churchmanagement.R;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.ActivityAdvertisementBinding;
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

public class AdvertisementActivity extends BaseActivity implements View.OnClickListener, GetResult.MyListener, RecyclerViewClickInterface {

    ActivityAdvertisementBinding binding;
    private final int CGALLERY = 1;
    String user_reqid = "";
    List<AllAdvertisement> allAdvertisements;
    String eventencodedImage = "", imgname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdvertisementBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        if (getIntent().getStringExtra("req_type").equalsIgnoreCase("news")) {
            setTitle("News");
            binding.titleHead.setText("News Title");
            if (!Utils.getSharedPreference().getString("username", "").equalsIgnoreCase("admin@gmail.com")) {
                binding.btnAdd.setVisibility(View.GONE);
                binding.btnView.setVisibility(View.GONE);
                binding.addView.setVisibility(View.GONE);
                getAllAdvertisements();
            }


        } else {

            setTitle("Advertisement");
        }

        ActionBarUtils.setActionBar(this, true);
        initViews(binding);
    }

    private void initViews(ActivityAdvertisementBinding binding) {
        binding.btnAdd.setOnClickListener(this);
        binding.btnView.setOnClickListener(this);
        binding.btnAdAttach.setOnClickListener(this);
        requestPermissions();
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
                    binding.imgAdver.setVisibility(View.VISIBLE);
                    binding.imgAdver.setImageBitmap(bitmap);
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

                if (binding.edAdTitle.getText().toString().isEmpty() &&
                        binding.edAdDescription.getText().toString().isEmpty()) {

                    Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                } else if (eventencodedImage.isEmpty()) {

                    Toast.makeText(this, "Please choose an image", Toast.LENGTH_SHORT).show();
                } else {

                    addAdevertisemenmts();
                }


                break;

            case R.id.btn_view:

                if (binding.btnView.getText().toString().equalsIgnoreCase("View All")) {

                    binding.addView.setVisibility(View.GONE);
                    binding.btnAdd.setVisibility(View.GONE);
                    binding.btnView.setText("Go Back");
                    getAllAdvertisements();
                } else {
                    binding.addView.setVisibility(View.VISIBLE);
                    binding.rcvAllAdvertisement.setVisibility(View.GONE);
                    binding.tvNoDisplay.setVisibility(View.GONE);
                    binding.btnAdd.setVisibility(View.VISIBLE);
                    binding.btnView.setText("View All");


                }

                break;

            case R.id.btn_adAttach:

                if (!binding.btnAdAttach.getText().toString().equalsIgnoreCase("Remove")) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(galleryIntent, CGALLERY);

                } else {
                    binding.btnAdAttach.setText("Attach");
                    binding.imgAdver.setVisibility(View.GONE);
                    eventencodedImage = "";
                    imgname = "";
                }


                break;

        }

    }

    private void getAllAdvertisements() {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            if (!getIntent().getStringExtra("req_type").equalsIgnoreCase("news")) {

                jsonObject.put("advertsement", "1");

            } else {

                jsonObject.put("advertsement", "2");
            }

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllAdvertisements((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllAdvertisements");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void addAdevertisemenmts() {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("advertisement_name", binding.edAdTitle.getText().toString().trim());
            jsonObject.put("advertisement_description", binding.edAdDescription.getText().toString());
            jsonObject.put("image", eventencodedImage);
            jsonObject.put("posted_by", Utils.getSharedPreference().getString("username", ""));
            jsonObject.put("image_name", imgname);
            jsonObject.put("active", "1");
            if (!getIntent().getStringExtra("req_type").equalsIgnoreCase("news")) {

                jsonObject.put("type", "1");

            } else {

                jsonObject.put("type", "2");
            }

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().addAdvertisements((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "addAdvertisements");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {
        if (callNo.equalsIgnoreCase("addAdvertisements")) {
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


        } else if (callNo.equalsIgnoreCase("getAllAdvertisements")) {
            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            //allChurchEvents = new ArrayList<>();
            if (example.getResultData().getAllAdvertisement() == null) {
                hideProgressWheel(true);
                binding.tvNoDisplay.setVisibility(View.VISIBLE);
                binding.rcvAllAdvertisement.setVisibility(View.GONE);

            }


            else {
                allAdvertisements = example.getResultData().getAllAdvertisement();
                hideProgressWheel(true);
                binding.rcvAllAdvertisement.setVisibility(View.VISIBLE);

                AdvertisementAdapter adapter = new AdvertisementAdapter(AdvertisementActivity.this, allAdvertisements, this, getIntent().getStringExtra("req_type"));
                binding.rcvAllAdvertisement.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvAllAdvertisement.setAdapter(adapter);


            }

        } else {
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
    }

    private void clearAllFields() {

        binding.edAdTitle.setText("");
        binding.edAdDescription.setText("");
        eventencodedImage = "";
        imgname = "";
        binding.imgAdver.setVisibility(View.GONE);
        binding.btnAdAttach.setText("Attach");
    }

    @Override
    public void onItemClick(int position, String chk) {

        user_reqid = allAdvertisements.get(position).getId();

        if (chk.equalsIgnoreCase("DELETE")) {
            showProgressWheel();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", user_reqid);
                JsonParser jsonParser = new JsonParser();
                Call<JsonObject> call = APIClient.getInterface().deleteAdvertisements((JsonObject) jsonParser.parse(jsonObject.toString()));
                GetResult getResult = new GetResult();
                getResult.setMyListener(this);
                getResult.onNCHandle(call, "deleteAdvertisements");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}