package com.example.myyapproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LogoActivity extends AppCompatActivity {

    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        logo = findViewById(R.id.imageView);
        NavigateToLoginPage();
    }

    public void NavigateToLoginPage(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                startActivity(intent);            }
        }, 1000);
    }
}
