package com.basitple.radioapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRegistrationActivity extends AppCompatActivity {

    Button register;
    EditText editName;
    EditText editEmail;
    EditText editEmailRe;
    ArrayList<Rating> defaultRatingsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        defaultRatingsList.add(new Rating(0L));
        register = (Button) findViewById(R.id.button8);
        editName = (EditText) findViewById(R.id.editText3);
        editEmail = (EditText) findViewById(R.id.editText4);
        editEmailRe = (EditText) findViewById(R.id.editText6);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().toString().equals("") && editEmail.getText().toString().equals("") &&
                        editEmailRe.getText().toString().equals("") &&
                        editEmail.getText().toString().equals(editEmailRe.getText().toString())) {

                    senddatatoserver(v);
                    Log.e("reply", "added");
                } else {
                    editEmail.setText("");
                    editEmailRe.setText("");
                }
            }
        });

    }

    //does POST request in background
    public void senddatatoserver(View v) {
        final String editEmailText = editEmail.getText().toString();
        final String editNameText = editName.getText().toString();
        int newId = -1;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final SCService scService = retrofit.create(SCService.class);
        scService.getUsers("").enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                int newId = response.body().users.size()+1;
                defaultRatingsList.get(0).setUserId(newId);
                //enqueue automatically does Call in background
                scService.registerUser(newId, editNameText, editEmailText, defaultRatingsList).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Log.d("Body", response.body().toString());
                        } else {
                            Log.d("Body", "null");
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {

            }
        });


    }
}

