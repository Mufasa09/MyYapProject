package com.example.myyapproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.function.Consumer;


public class LoginActivity extends AppCompatActivity {


    Button createNewAccountBtn;
    Button loginBtn;
    ImageView googleBtn;
    SignInClient oneTapClient;
    BeginSignInRequest signUpRequest;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        createNewAccountBtn = findViewById(R.id.CreateNewAccount);
        loginBtn = findViewById(R.id.SignInButton);
        TextView username =  findViewById(R.id.username);
        TextView password =  findViewById(R.id.password);
        googleBtn = findViewById(R.id.googleImage);


        createNewAccountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(v -> {
            if(username.getText().toString().equals("admin")&&
            password.getText().toString().equals("admin"))
            {
                Toast.makeText(LoginActivity.this,"Login is success", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoginActivity.this,"Login failed", Toast.LENGTH_SHORT).show();
            }
        });

        //region Google Sign on


        oneTapClient = Identity.getSignInClient(this);
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.web_client_id))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        ActivityResultLauncher<IntentSenderRequest> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(),
                        result -> {
                            if(result.getResultCode()== Activity.RESULT_OK)
                            {
                                try {
                                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                                    String idToken = credential.getGoogleIdToken();
                                    if (idToken !=  null) {
                                        email = credential.getId();
                                        //Attempting to pass email to the fragment
                                        /*FragmentManager fragmentManager = getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("email", email );
                                        FirstFragment fragInfo = new FirstFragment();
                                        fragInfo.setArguments(bundle);
                                        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragInfo ).commit();*/

                                        Toast.makeText(getApplicationContext(),"Email: " +email, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                } catch (ApiException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

        googleBtn.setOnClickListener(v -> oneTapClient.beginSignIn(signUpRequest)
                .addOnSuccessListener(LoginActivity.this,
                        result -> {
                            //
                            IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(result.getPendingIntent().
                                    getIntentSender()).
                                    build();
                            activityResultLauncher.launch(intentSenderRequest);
                        })
                .addOnFailureListener(LoginActivity.this,
                        e -> Log.d("TAG", e.getLocalizedMessage())));
        //endregion
    }


    }

