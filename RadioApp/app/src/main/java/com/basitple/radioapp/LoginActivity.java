package com.basitple.radioapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
// added the login possibility
//get some skiped frame errors, but it will not crash the app
//i am using the production server. since i am not making any changes to it.
// yo can try

public class LoginActivity extends AppCompatActivity {

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
        login = (Button)findViewById(R.id.button);
        newUser = (Button)findViewById(R.id.button9);
        toHome = new Intent(LoginActivity.this, HomePageActivity.class);

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
                LoginBackgroundTask loginBackgroundTask = new LoginBackgroundTask(LoginActivity.this);
                try {
                    String output=  loginBackgroundTask.execute(method,userName,userEmail).get();
                    Log.e("user",output);
                    if(output.equalsIgnoreCase(userName)){
                        startActivity(toHome);
                        Toast.makeText(LoginActivity.this,"welcome " + userName + "!",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(LoginActivity.this,"register",Toast.LENGTH_LONG).show();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        newUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent toRegister = new Intent(LoginActivity.this, MainRegistrationActivity.class);
                startActivity(toRegister);
            }
        });

    }
}

