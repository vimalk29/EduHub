package com.eduhub.company.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.eduhub.company.R;
import com.eduhub.company.fragments.nav_frag.Chat;
import com.eduhub.company.fragments.nav_frag.Home;
import com.eduhub.company.fragments.nav_frag.Info;
import com.eduhub.company.fragments.nav_frag.Profile;
import com.eduhub.company.fragments.nav_frag.Ask;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.navigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.navigationHome:

                        Fragment nextFrag= new Home();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, nextFrag)
                                .addToBackStack(null)
                                .commit();

                        break;
                    case R.id.navigetionChat:

                        Fragment nextFragC= new Chat();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, nextFragC)
                                .addToBackStack(null)
                                .commit();

                        break;
                    case R.id.navigationUpload:

                        Fragment nextFragU= new Ask();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, nextFragU)
                                .addToBackStack(null)
                                .commit();

                        break;
                    case R.id.navigationInfo:

                        Fragment nextFragI= new Info();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, nextFragI)
                                .addToBackStack(null)
                                .commit();

                        break;
                    case R.id.navigationProfile:

                        Fragment nextFragP= new Profile();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, nextFragP)
                                .addToBackStack(null)
                                .commit();

                        break;
                }
                return true;
            }
        });
    }
}
