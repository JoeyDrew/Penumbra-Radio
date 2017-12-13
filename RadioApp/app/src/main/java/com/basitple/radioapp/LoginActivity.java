package com.basitple.radioapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private Button newUser;
    private EditText loginName;
    private EditText loginEmail;
    Intent toHome;
    Intent toRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginName = (EditText)findViewById(R.id.user_name_login);
        loginEmail = (EditText)findViewById(R.id.user_Email_login);
        login = (Button)findViewById(R.id.LOGIN_BUTTON);
        newUser = (Button)findViewById(R.id.NEW_USE_BUTTON);
        login.setOnClickListener(this);
        newUser.setOnClickListener(this);
        toHome = new Intent(LoginActivity.this, MainActivity.class);
        toRegister = new Intent(LoginActivity.this, MainRegistrationActivity.class);

    }
    @Override
    public void onClick(View v){
        Log.d("Click registered", v.toString());
        Log.d("v.Id", Integer.toString(v.getId()));
        Log.d("login.id", Integer.toString(login.getId()));
        if(v.getId() == login.getId()){
            Log.d("match", "match");
            try {
                AsyncTask loginTask = new LoginTask(loginName.getText().toString()
                        , loginEmail.getText().toString()
                        , new AsyncResponse<Boolean>() {
                    @Override
                    public void processFinish(Boolean value) {
                        Log.d("value", Boolean.toString(value));
                        if (value) {
                            startActivity(toHome);
                        } else {
                            Toast.makeText(LoginActivity.this, "Please register", Toast.LENGTH_LONG).show();
                            loginName.setText("");
                            loginEmail.setText("");
                        }
                    }
                }).execute();
            } catch(Exception e){
                e.printStackTrace();
            }
        } else if(v == newUser){
            startActivity(toRegister);
        }
    }
}

