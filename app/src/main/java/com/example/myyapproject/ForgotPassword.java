package com.example.myyapproject;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView textView;
    Button backButton, resetButon;

    EditText editEmail;
    ProgressBar resetProgressBar;

    String strEmail;



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        //Initialization
        mAuth = FirebaseAuth.getInstance();

        backButton = findViewById(R.id.back_button);
        resetButon = findViewById(R.id.reset_password);
        editEmail = findViewById(R.id.email);
        resetProgressBar = findViewById(R.id.resetProgressBar);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        resetButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = editEmail.getText().toString().trim();

                if(!TextUtils.isEmpty(strEmail)){
                    ResetPassword();
                }else{
                    editEmail.setError("Email field can't be empty");
                    Toast.makeText(ForgotPassword.this, "Email field can't be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

            backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ResetPassword(){
        resetProgressBar.setVisibility(View.VISIBLE);
        resetButon.setVisibility(View.INVISIBLE);

        mAuth.sendPasswordResetEmail(strEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgotPassword.this, "Reset password link has been sent to your email ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgotPassword.this, "Error:- " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        resetProgressBar.setVisibility(View.INVISIBLE);
                        resetButon.setVisibility(View.VISIBLE);
                    }
                });
    }
}

