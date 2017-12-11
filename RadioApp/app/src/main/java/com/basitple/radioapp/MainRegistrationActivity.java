package com.basitple.radioapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainRegistrationActivity extends AppCompatActivity {

    Button register;
    EditText editName;
    EditText editEmail;
    EditText editEmailRe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        register = (Button)findViewById(R.id.button8);
        editName = (EditText)findViewById(R.id.editText3);
        editEmail = (EditText)findViewById(R.id.editText4);
        editEmailRe = (EditText)findViewById(R.id.editText6);

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(editEmail.getText().toString().equals(editEmailRe.getText().toString()))
                {
                    finish();
                } else {
                    editEmail.setText("");
                    editEmailRe.setText("");
                }
            }
        });

    }

}
