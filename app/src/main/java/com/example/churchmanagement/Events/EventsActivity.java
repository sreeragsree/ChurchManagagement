package com.example.churchmanagement.Events;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.Ministry.AllMinistry;
import com.example.churchmanagement.Ministry.MinistryActivity;
import com.example.churchmanagement.Ministry.MinistryAdapter;
import com.example.churchmanagement.Model.Example;
import com.example.churchmanagement.Model.ResponseCommon;
import com.example.churchmanagement.R;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.ActivityEventsBinding;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class EventsActivity extends BaseActivity implements View.OnClickListener, GetResult.MyListener, RecyclerViewClickInterface {

    ActivityEventsBinding binding;
    List<AllChurchEvent> allChurchEvents;
    private final int CGALLERY = 1;
    String update_id="";
    String eventencodedImage = "", imgname = "";
    final Calendar myCalendar = Calendar.getInstance();

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
        binding.btnAdd.setOnClickListener(this);
        binding.btnView.setOnClickListener(this);
        binding.btnAdAttach.setOnClickListener(this);
        binding.edStartDate.setOnClickListener(this);
        binding.edEndDate.setOnClickListener(this);
        if (!Utils.getSharedPreference().getString("username", "").equalsIgnoreCase("admin@gmail.com")) {

            binding.btnAdd.setVisibility(View.GONE);
            binding.btnView.setVisibility(View.GONE);
            binding.addView.setVisibility(View.GONE);
            binding.rcvEvents.setVisibility(View.VISIBLE);
            getAllChurchEvents();
        }
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

                if (binding.btnAdd.getText().toString().equalsIgnoreCase("UPDATE")) {

                    editChurchEvents(update_id);


                } else {
                    if (binding.edAdTitle.getText().toString().isEmpty() &&
                            binding.edAdDescription.getText().toString().isEmpty() &&
                            binding.edStartDate.getText().toString().isEmpty() &&
                            binding.edEndDate.getText().toString().isEmpty()) {

                        Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                    } else if (eventencodedImage.isEmpty()) {

                        Toast.makeText(this, "Please choose an image", Toast.LENGTH_SHORT).show();
                    } else {

                        addChurchEvents();
                    }
                }
                break;

            case R.id.btn_view:
                if (binding.btnView.getText().toString().equalsIgnoreCase("View All")) {

                    binding.addView.setVisibility(View.GONE);
                    binding.btnAdd.setVisibility(View.GONE);
                    binding.btnView.setText("Go Back");
                    getAllChurchEvents();
                } else {
                    binding.addView.setVisibility(View.VISIBLE);
                    binding.rcvEvents.setVisibility(View.GONE);
                    binding.tvNoDisplay.setVisibility(View.GONE);
                    binding.btnAdd.setVisibility(View.VISIBLE);
                    binding.btnView.setText("View All");


                }

                break;

            case R.id.ed_startDate:

                showDatepopup();
                break;

            case R.id.ed_endDate:

                showDatepopup();
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


        }
    }

    private void getAllChurchEvents() {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("events", "0");
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllChurchEvents((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllChurchEvents");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void showDatepopup() {

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };


        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (binding.edStartDate.getText().toString().isEmpty()) {
            binding.edStartDate.setText(sdf.format(myCalendar.getTime()));
        } else {
            binding.edEndDate.setText(sdf.format(myCalendar.getTime()));


        }
    }

    private void addChurchEvents() {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_name", binding.edAdTitle.getText().toString().trim());
            jsonObject.put("event_description", binding.edAdDescription.getText().toString());
            jsonObject.put("image", eventencodedImage);
            jsonObject.put("image_name", imgname);
            jsonObject.put("start_date", binding.edStartDate.getText().toString());
            jsonObject.put("end_date", binding.edEndDate.getText().toString());
            jsonObject.put("active", "1");
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().addChurchEvents((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "addChurchEvents");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("addChurchEvents")) {
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


        } else if (callNo.equalsIgnoreCase("getAllChurchEvents")) {
            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);
            allChurchEvents = new ArrayList<>();
            if (example.getResultData().getAllChurchEvents() == null) {
                hideProgressWheel(true);
                binding.tvNoDisplay.setVisibility(View.VISIBLE);
                binding.rcvEvents.setVisibility(View.GONE);

            } else {
                allChurchEvents = example.getResultData().getAllChurchEvents();
                hideProgressWheel(true);
                binding.rcvEvents.setVisibility(View.VISIBLE);

                EventAdapter adapter = new EventAdapter(EventsActivity.this, allChurchEvents, this);
                binding.rcvEvents.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvEvents.setAdapter(adapter);


            }


        } else if(callNo.equalsIgnoreCase("updateChurchEvents")){
            Gson gson=new Gson();
            ResponseCommon responseCommon=gson.fromJson(result.toString(),ResponseCommon.class);
            hideProgressWheel(true);
            if(responseCommon.getResult().equalsIgnoreCase("true")){

                showAlert(responseCommon.getResponseMsg(),"Success");

            }
            else{
                showAlert(responseCommon.getResponseMsg(),"Error");

            }

        }

        else {
            Gson gson=new Gson();
            ResponseCommon responseCommon=gson.fromJson(result.toString(),ResponseCommon.class);
            hideProgressWheel(true);
            if(responseCommon.getResult().equalsIgnoreCase("true")){

                showAlert(responseCommon.getResponseMsg(),"Success");

            }
            else{
                showAlert(responseCommon.getResponseMsg(),"Error");

            }

        }

    }

    private void clearAllFields() {
        binding.edAdTitle.setText("");
        binding.edStartDate.setText("");
        binding.edEndDate.setText("");
        binding.edAdDescription.setText("");
        eventencodedImage = "";
        imgname = "";
        binding.imgEvent.setVisibility(View.GONE);
        binding.btnAdAttach.setText("Attach");
    }


    @Override
    public void onItemClick(int position, String chk) {
         update_id = allChurchEvents.get(position).getId();
        if (chk.equalsIgnoreCase("EDIT")) {
            binding.rcvEvents.setVisibility(View.GONE);
            binding.btnView.setVisibility(View.GONE);
            binding.addView.setVisibility(View.VISIBLE);
            binding.btnAdd.setVisibility(View.VISIBLE);
            binding.attchLayout.setVisibility(View.GONE);
            binding.btnAdd.setText("UPDATE");
            binding.edAdTitle.setText(allChurchEvents.get(position).getEventName());
            binding.edAdDescription.setText(allChurchEvents.get(position).getEventDescription());
            binding.edStartDate.setText(allChurchEvents.get(position).getStartDate());
            binding.edEndDate.setText(allChurchEvents.get(position).getEndDate());

        } else {

            deleteChurchEvents(update_id);
        }

    }

    private void editChurchEvents(String id) {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("event_name", binding.edAdTitle.getText().toString());
            jsonObject.put("event_description", binding.edAdDescription.getText().toString());
            jsonObject.put("start_date", binding.edStartDate.getText().toString());
            jsonObject.put("end_date", binding.edEndDate.getText().toString());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().updateChurchEvents((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "updateChurchEvents");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void deleteChurchEvents(String id) {
        showProgressWheel();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().deleteChurchEvents((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "deleteChurchEvents");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}