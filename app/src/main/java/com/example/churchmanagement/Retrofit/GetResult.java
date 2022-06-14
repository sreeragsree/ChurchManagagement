package com.example.churchmanagement.Retrofit;

import android.util.Log;

import com.example.churchmanagement.BaseActivity;
import com.example.churchmanagement.utils.App;
import com.example.churchmanagement.utils.Utils;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetResult {
    public static MyListener myListener;

    public void onNCHandle(Call<JsonObject> call, String res) {

        if (!Utils.isNetworkAvailable(App.instance())) {
            Utils.showToast(App.instance(), "Please Check Your Internet Connection");

        } else {

            call.enqueue(new Callback<JsonObject>() {
                @Override

                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.e("message", " : " + response.message());
                    Log.e("body", " : " + response.body());
                    Log.e("callno", " : " + res);
                    myListener.callback(response.body(), res);
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    myListener.callback(null, res);
//                    Log.d("failed000",res);
                    call.cancel();
                    t.printStackTrace();
                }
            });
        }
    }

    public interface MyListener {

        public void callback(JsonObject result, String callNo);
    }
    public void setMyListener(MyListener Listener) {
        myListener = Listener;
    }
}
