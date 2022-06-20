package com.example.churchmanagement.Booking;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.churchmanagement.Appointment.AppointmentAdapter;
import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.Interface.RecyclerViewClickInterface;
import com.example.churchmanagement.Model.ResponseCommon;
import com.example.churchmanagement.R;
import com.example.churchmanagement.Retrofit.APIClient;
import com.example.churchmanagement.Retrofit.GetResult;
import com.example.churchmanagement.databinding.AllbookingCardBinding;
import com.example.churchmanagement.databinding.AppointmentCardBinding;
import com.example.churchmanagement.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> implements GetResult.MyListener {

    Context context;
    List<AllAmenity> allAmenities = new ArrayList<>();
    RecyclerViewClickInterface recyclerViewClickInterface;
    String view_type="";
    String book_date = "";
    ArrayList<String> arrayList = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();

    public BookingAdapter(Context context, List<AllAmenity> allAmenities, RecyclerViewClickInterface recyclerViewClickInterface,String view_type) {
        this.context = context;
        this.allAmenities = allAmenities;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
        this.view_type=view_type;
    }

    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

           return  new  ViewHolder(AllbookingCardBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.ViewHolder holder, int position) {
        AllAmenity amenity = allAmenities.get(position);
        arrayList.add("Choose a time");
        arrayList.add("11:00 AM");
        arrayList.add("3:00 PM");
        arrayList.add("5:00 PM");
        arrayList.add("8:00 PM");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.binding.choosePurpose.setAdapter(arrayAdapter);
        holder.binding.adverDescription.setText(amenity.getDescription());
        holder.binding.tvCampname.setText(amenity.getBookingFor());
        Glide.with(context).load(APIClient.baseUrl + amenity.getImage()).into(holder.binding.ivBanner);

        if (Utils.getSharedPreference().getString("username", "").equalsIgnoreCase("admin@gmail.com")) {
            holder.binding.btnDelete.setVisibility(View.VISIBLE);
            holder.binding.btnEdit.setVisibility(View.VISIBLE);
            holder.binding.addBooking.setVisibility(View.GONE);

        }
        else{

            getBookingCStatus(holder, amenity.getId(), Utils.getSharedPreference().getString("username", ""));

        }


    }

    private void getBookingCStatus(ViewHolder holder, String id, String username) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("a_id", id);
            jsonObject.put("username", username);
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getBookingCStatus((JsonObject) jsonParser.parse(jsonObject.toString()));
            call.enqueue(new Callback<JsonObject>() {
                @Override

                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    Gson gson = new Gson();
                    JsonObject object=response.body();
                    ResponseCommon responseCommon = gson.fromJson(object.toString(), ResponseCommon.class);
                    if (!responseCommon.getResult().equalsIgnoreCase("true")) {
                        holder.binding.tvStatus.setText("CONFIRMED");
                        holder.binding.tvStatus.setBackgroundColor(context.getResources().getColor(R.color.green));
                        holder.binding.addBooking.setVisibility(View.GONE);
                    }
                    else{
                        holder.binding.tvStatus.setText("NOT CONFIRMED");
                        holder.binding.tvStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
                        holder.binding.addBooking.setVisibility(View.GONE);

                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return allAmenities.size();
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        Gson gson = new Gson();
        ResponseCommon responseCommon = gson.fromJson(result.toString(), ResponseCommon.class);
        if (responseCommon.getResult().equalsIgnoreCase("true")) {
            Toast.makeText(context, responseCommon.getResponseMsg(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, responseCommon.getResponseMsg(), Toast.LENGTH_SHORT).show();

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AllbookingCardBinding binding;

        public ViewHolder(@NonNull AllbookingCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.dpicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatepopup(binding);
                }
            });

            binding.viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition(),"VIEWALL");
                }
            });

            binding.addBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.tvChooseText.setVisibility(View.VISIBLE);
                    binding.choosePurpose.setVisibility(View.VISIBLE);
                    binding.dpicker.setVisibility(View.VISIBLE);
                    if (book_date == "" && binding.choosePurpose.getSelectedItem().toString().equalsIgnoreCase("Choose a time")) {

                        Toast.makeText(context, "Please Choose a Specific Date and Time", Toast.LENGTH_SHORT).show();
                    } else {
                        completeBooking(allAmenities.get(getAdapterPosition()).getId(), book_date
                                , binding.choosePurpose.getSelectedItem().toString()
                                , Utils.getSharedPreference().getString("username", ""));
                    }
                }
            });
        }
    }

    private void completeBooking(String id, String book_date, String time, String username) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("book_date", book_date);
            jsonObject.put("time", time);
            jsonObject.put("username", username);
            jsonObject.put("amen_id", id);
            jsonObject.put("status", "0");
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().addConfirmBookingRequest((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "addConfirmBookingRequest");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void showDatepopup(AllbookingCardBinding binding) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(binding);

            }

        };


        new DatePickerDialog(context, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void updateLabel(AllbookingCardBinding binding) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        book_date = sdf.format(myCalendar.getTime());


    }
}
