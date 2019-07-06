package com.eduhub.company.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.eduhub.company.R;
import com.eduhub.company.activities.parent.MainActivityParent;
import com.eduhub.company.activities.teacher.MainActivityT;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth auth;
    boolean flag =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        auth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (auth.getCurrentUser() != null) {
                    startActivity(new Intent(SplashScreen.this, MainActivityT.class));
                    Log.d("1234","teacher logged in");
                    //Toast.makeText(SplashScreen.this, "LoggedIn", Toast.LENGTH_LONG).show();
                    finish();
                    flag=true;

                }
                try{
                    if(getSharedPreferences("mypref", MODE_PRIVATE).getString("type",null).equals("P")){
                        startActivity(new Intent(SplashScreen.this, MainActivityParent.class));
                        //Toast.makeText(SplashScreen.this, "LoggedIn", Toast.LENGTH_LONG).show();
                        finish();
                        flag=true;
                    }
                    if(getSharedPreferences("mypref", MODE_PRIVATE).getString("type",null).equals("S")){
                        startActivity(new Intent(SplashScreen.this, MainActivityStudent.class));
                        //Toast.makeText(SplashScreen.this, "LoggedIn", Toast.LENGTH_LONG).show();
                        finish();
                        flag=true;
                    }
                }catch (Exception e){
                    Log.d("1234", "run: "+e.getMessage());
                }
                if (!flag){
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
