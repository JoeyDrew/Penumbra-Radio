package com.basitple.radioapp;


import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginTask extends AsyncTask {
    private String UserName_In;
    private String Email_In;
    public AsyncResponse<Boolean> Delegate = null;
    public LoginTask(String userName_In, String email_In, AsyncResponse<Boolean> delegate) {
        Delegate = delegate;
        UserName_In = userName_In;
        Email_In = email_In;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SCService scService = retrofit.create(SCService.class);
        scService.getUsers("").enqueue(new Callback<UsersList>(){
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response){
                if(response.isSuccessful()) {
                    List<User> usersList = response.body().users;
                    for (User user : usersList) {
                        Log.d(user.getName(), UserName_In);
                        Log.d(user.getEmail(), Email_In);
                        if (user.getName().equals(UserName_In)  && user.getEmail().equals(Email_In)) {
                            Delegate.processFinish(true);
                            return;
                        }
                    }
                    Delegate.processFinish(false);
                } else {
                    Log.e("Response failure", response.toString());
                }


            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
                Log.e("Response Failure", t.getMessage());
                t.printStackTrace();
            }

        });
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
