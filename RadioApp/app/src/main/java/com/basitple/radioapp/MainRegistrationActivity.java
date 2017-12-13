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

    public class MainRegistrationActivity extends AppCompatActivity {

        Button register;
        EditText editName;
        EditText editEmail;
        EditText editEmailRe;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registration);

            register = (Button) findViewById(R.id.button8);
            editName = (EditText) findViewById(R.id.editText3);
            editEmail = (EditText) findViewById(R.id.editText4);
            editEmailRe = (EditText) findViewById(R.id.editText6);

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editEmail.getText().toString().equals(editEmailRe.getText().toString())) {
                        senddatatoserver(v);
                        finish();
                        Log.e("reply", "added");
                    } else {
                        editEmail.setText("");
                        editEmailRe.setText("");
                    }
                }
            });

        }

        public void senddatatoserver(View v) {
            //function in the activity that corresponds to the layout button
            String editEmailText = editEmail.getText().toString();
            String editNameText = editName.getText().toString();
            //  Age = AgeView.getText().toString();
            JSONObject wrap = new JSONObject();
            JSONObject new_user = new JSONObject();
            JSONArray rating = new JSONArray();
            try {
                new_user.put("email", editEmailText);
                new_user.put("name", editNameText);
                // post_dict.put("firstname", Age);
                wrap.put("user", new_user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (new_user.length() > 0) {
                new sendJsonDataToServer().execute(String.valueOf(wrap));
            }
        }
    }
    class sendJsonDataToServer extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                String url_add = "http://18.221.90.240:3000/users";
                URL url = new URL(url_add);
                urlConnection = (HttpURLConnection) url.openConnection();
                // urlConnection.setDoOutput(true);
                // is output buffer writter
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();
                //urlConnection.setRequestProperty("Accept", "application/json");
//set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);
// json data
                writer.close();
                InputStream inputStream = urlConnection.getInputStream();
//input stream
                StringBuffer buffer = new StringBuffer();
                // reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                Log.e("onject", JsonDATA);
                //String inputLine;
                //while ((inputLine = reader.readLine()) != null)
                //  buffer.append(inputLine + "\n");
                //if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                //  return null;
                //}
                JsonResponse = buffer.toString();
//response data
                Log.i("server:", "hello");
//send to post execute
                return "hello";
                //JsonResponse;

            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("ERROR", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


