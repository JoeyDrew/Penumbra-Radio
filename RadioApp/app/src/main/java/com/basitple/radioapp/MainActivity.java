package com.basitple.radioapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
// added the login possibility
//get some skiped frame errors, but it will not crash the app
//i am using the production server. since i am not making any changes to it.
// yo can try
public class MainActivity extends AppCompatActivity {

    Button login;
    EditText loginName;
    EditText loginEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginName = (EditText)findViewById(R.id.user_name_login);
        loginEmail = (EditText)findViewById(R.id.user_Email_login);
        login = (Button)findViewById(R.id.button);

        // perform setOnClickListener on first Button
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Load back activity fragment
                String method = "login";
                String userName;
                String userEmail;
                userEmail = loginEmail.getText().toString();
                userName = loginName.getText().toString();
                LoginBackgroundTask loginBackgroundTask = new LoginBackgroundTask(MainActivity.this);
                try {
                    String output=  loginBackgroundTask.execute(method,userName,userEmail).get();
                    Log.e("user",output);
                    if(output.equalsIgnoreCase(userName)){
                        Intent toMain = new Intent(MainActivity.this, Main_page2.class);
                        startActivity(toMain);
                        Toast.makeText(MainActivity.this,"welcome " + userName + "!",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(MainActivity.this,"register",Toast.LENGTH_LONG).show();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //Method to Hiding Navigation Bar
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}

// class that does the background login task.
 class LoginBackgroundTask extends AsyncTask<String, Void, String> {
    private Context ctx;


    LoginBackgroundTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        //local server addr
        String url_add = "http://18.221.90.240:3000/users";
        // string to check if user wants to log in.
        String method = params[0];
        String userEmail = params[2];
        if (method.equals("login")) {
            // user wants to login process
            // http connection to server
            HttpURLConnection connection = null;
            // to read server server input Stream.
            BufferedReader reader = null;
            /////////
            try {
                //url string conversion
                URL url = new URL(url_add);
                //connect to server
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                //grab input stream from server.
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                String line = "";
                StringBuffer buffer = new StringBuffer();
                //read all input Stream.
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                // convert input Stream to string
                String finalJson = buffer.toString();
                /// make a jsonObject
                JSONObject parentOnject = new JSONObject(finalJson);
                ///grab the array in json:users
                JSONArray users = parentOnject.getJSONArray("users");
                JSONObject user;
                String welcome = "";

                //iterate through the list to get email and id.
                for (int i = 0; i < users.length(); i++) {
                    user = users.getJSONObject(i);
                    if (userEmail.equalsIgnoreCase(user.getString("email"))) {
                        welcome = user.getString("name");
                        Log.e("msg", welcome);
                        break;
                    } else {
                        welcome = "you need to register";
                    }
                    // Log.e("email", user.getString("email").toString());
                }
                stream.close();
                return welcome;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } //catch (JSONException e) {
            // e.printStackTrace();
            //}
            catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
            return null;
        }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String out) {
       // Log.e("msg", buffer.toString());
        //Toast.makeText(ctx, "welcome" + out,Toast.LENGTH_LONG).show();
    }
}