package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Auth.SplashFragment;
import com.example.myapplication.Data.Helper.Database;

public class AuthenticationActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        sharedPreferences = getSharedPreferences("Inventory", MODE_PRIVATE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.authentication_container, new SplashFragment())
                .commit();
    }

    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }
}