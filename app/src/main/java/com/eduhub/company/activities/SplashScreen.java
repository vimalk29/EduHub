package com.eduhub.company.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.eduhub.company.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser() != null) {
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                    Toast.makeText(SplashScreen.this, "LoggedIn", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, FirstActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1500);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
