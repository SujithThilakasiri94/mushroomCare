package com.example.mushroomcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {
    EditText hName, hEmail, hPassword, hPhone;
    Button gBtnRegister;
    TextView linkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        hName = findViewById(R.id.nameRe);
        hEmail = findViewById(R.id.email);
        hPassword = findViewById(R.id.password);
        gBtnRegister = findViewById(R.id.btnRegister);
        linkLogin = findViewById(R.id.loginLink);

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


    }
}