package com.basitple.radioapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
// added the login possibility
//get some skiped frame errors, but it will not crash the app
//i am using the production server. since i am not making any changes to it.
// yo can try

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private Button newUser;
    private EditText loginName;
    private EditText loginEmail;
    Intent toHome;

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
        toHome = new Intent(LoginActivity.this, HomePageActivity.class);

    }
    @Override
    public void onClick(View v){
        Log.d("Click registered", v.toString());
        Log.d("v.Id", Integer.toString(v.getId()));
        Log.d("login.id", Integer.toString(login.getId()));
        if(v.getId() == login.getId()){
            Log.d("match", "match");
            new LoginTask(loginName.getText().toString()
                    , loginEmail.getText().toString()
                    , new AsyncResponse<Boolean>() {
                @Override
                public void processFinish(Boolean value) {
                    Log.d("value", Boolean.toString(value));
                    if(value){
                        startActivity(toHome);
                    }
                }
            }).execute();
        } else if(v == newUser){

        }
    }
}

