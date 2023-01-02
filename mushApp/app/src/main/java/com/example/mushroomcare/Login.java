package com.example.mushroomcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Login extends AppCompatActivity {

    EditText gEmai,gPassword;
    Button gBtnLogin;
    TextView gCreateView, forgetTextLink;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gEmai = findViewById(R.id.email);
        gPassword = findViewById(R.id.password);
        gBtnLogin = findViewById(R.id.btnRegister);
        gCreateView = findViewById(R.id.CreateView); //create new account
        forgetTextLink =findViewById(R.id.forgotpassword);

        gBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = gEmai.getText().toString().trim();
                String password = gPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    gEmai.setError("Email is Required!");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    gPassword.setError("Password is Required!");
                    return;
                }

                if (password.length()<=4){
                    gPassword.setError("Password must be longer than 4 characters!");
                    return;
                }
                if (password.length()>=4){

                    startActivity(new Intent(getApplicationContext(),RealTimeUpdate.class));
                }
            }
        });

        gCreateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });


        forgetTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordReserDialog = new AlertDialog.Builder(v.getContext());
                passwordReserDialog.setTitle("Reset Password?");
                passwordReserDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordReserDialog.setView(resetMail);

                passwordReserDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the email and send reset link
                        String mail = resetMail.getText().toString();

                    }
                });
                passwordReserDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the program
                    }
                });
                passwordReserDialog.create().show();

            }
        });

    }
}