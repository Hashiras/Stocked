package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Menu.AccountFragment;
import com.example.myapplication.Menu.HomeFragment;
import com.example.myapplication.Menu.ListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Inventory", MODE_PRIVATE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, new HomeFragment())
                .commit();

        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setSelectedItemId(R.id.nav_home);
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_container, new HomeFragment())
                            .commit();
                    return true;
                case  R.id.nav_list:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_container, new ListFragment())
                            .commit();
                    return true;
                case R.id.nav_account:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_container, new AccountFragment())
                            .commit();
                    return true;
            }
            return false;
        });
    }

    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }
}
