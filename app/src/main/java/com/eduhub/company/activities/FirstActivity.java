package com.eduhub.company.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.eduhub.company.R;
import com.eduhub.company.fragments.UserLoginFragment;
import com.eduhub.company.helper.SelectImageHelper;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Fragment fragment = new UserLoginFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.activity_first_fragment, fragment);
        transaction.commit();
        transaction.addToBackStack(null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_first_fragment);
        fragment.onActivityResult(requestCode, resultCode, result);
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final @NonNull String[] permissions, final @NonNull int[] grantResults) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_first_fragment);
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
