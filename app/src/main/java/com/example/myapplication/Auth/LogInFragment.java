package com.example.myapplication.Auth;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.AuthenticationActivity;
import com.example.myapplication.Data.Credential;
import com.example.myapplication.Data.Helper.CredentialHelper;
import com.example.myapplication.R;

public class LogInFragment extends Fragment {
    public LogInFragment() {
        super(R.layout.fragment_log_in);
    }

    private int ID;
    private SharedPreferences sharedPreferences;
    private EditText username, password;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = ((AuthenticationActivity) getActivity()).getSharedPreferences();
        username = view.findViewById(R.id.log_in_username);
        password = view.findViewById(R.id.log_in_password);

        if(sharedPreferences.getInt("accountID", 0) != 0){
            proceed();
            Log.i("CredentialChecker", "Account logged in is: " + sharedPreferences.getInt("accountID", 0));
            return;
        }

        TextView link = view.findViewById(R.id.log_in_link);
        link.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.authentication_container, new RegisterFragment())
                    .commit();
        });
        Button button = view.findViewById(R.id.button2);
        button.setOnClickListener(v -> {
            if(!fieldsAreValid())
                return;
            if(validCredentials())
                login();
        });
    }

    private void login(){Bundle args = new Bundle();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("accountID", ID);
        editor.commit();
        proceed();
    }

    private void proceed(){
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.authentication_container, new PinFragment())
                .commit();
    }

    private boolean validCredentials(){
        boolean validCredentials = false;
        for(Credential credential : CredentialHelper.instance(getContext()).get()){
            if(credential.getUsername().equals(username.getText().toString()) &&
                credential.getPassword().equals(password.getText().toString())) {
                validCredentials = true;
                ID = credential.getID();
            }
        }
        return validCredentials;
    }

    private boolean fieldsAreValid() {
        boolean fieldsAreValid = true;
        if(username.getText().toString().isEmpty()){
            username.setError("Field is required!");
            fieldsAreValid = false;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Field is required!");
            fieldsAreValid = false;
        }
        return fieldsAreValid;
    }
}
