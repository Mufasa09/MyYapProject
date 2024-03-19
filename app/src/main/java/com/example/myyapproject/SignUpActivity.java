package com.example.myyapproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {


    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView username = (TextView) findViewById(R.id.signupEmail);
        TextView password = (TextView) findViewById(R.id.signupPassword);
        TextView confirmUsername = (TextView) findViewById(R.id.confirmSignupEmail);
        TextView confirmPassword = (TextView) findViewById(R.id.confirmSignupPassword);
        loginBtn = findViewById(R.id.signupButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals(confirmUsername.getText().toString()) &&
                password.getText().toString().equals(confirmPassword.getText().toString()))
                {
                    Toast.makeText(SignUpActivity.this,"Login is success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(SignUpActivity.this,"Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
