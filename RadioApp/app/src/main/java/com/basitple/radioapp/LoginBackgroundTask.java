package com.basitple.radioapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
