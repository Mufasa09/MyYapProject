package com.example.myyapproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    Button createNewAccountBtn;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createNewAccountBtn = findViewById(R.id.CreateNewAccount);
        loginBtn = findViewById(R.id.SignInButton);
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        createNewAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin")&&
                password.getText().toString().equals("admin"))
                {
                    Toast.makeText(LoginActivity.this,"Login success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
