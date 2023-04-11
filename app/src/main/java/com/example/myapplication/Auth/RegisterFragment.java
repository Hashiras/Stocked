package com.example.myapplication.Auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Data.Account;
import com.example.myapplication.Data.Credential;
import com.example.myapplication.Data.Enums.AccountEnum;
import com.example.myapplication.Data.Helper.AccountHelper;
import com.example.myapplication.Data.Helper.CredentialHelper;
import com.example.myapplication.R;

public class RegisterFragment extends Fragment {
    public RegisterFragment() {
        super(R.layout.fragment_register);
    }

    private EditText fullName, email, username, password, password2;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullName = view.findViewById(R.id.register_full_name);
        email = view.findViewById(R.id.register_email);
        username = view.findViewById(R.id.register_username);
        password = view.findViewById(R.id.register_password);
        password2 = view.findViewById(R.id.register_password2);


        TextView link = view.findViewById(R.id.register_link);
        link.setOnClickListener(v -> proceed());

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(v -> {
            if(!fieldsAreValid())
                return;
            createAccount();
            proceed();
        });
    }

    private void createAccount(){
        Credential credential = new Credential();
        credential.setAccountID(AccountHelper.instance(getContext()).getNextID());
        credential.setUsername(username.getText().toString());
        credential.setPassword(password.getText().toString());

        Account account = new Account();
        account.setFullName(fullName.getText().toString());
        account.setEmail(email.getText().toString());
        account.setType(AccountEnum.USER);
        account.setCredentialID(CredentialHelper.instance(getContext()).getNextID());

        AccountHelper.instance(getContext()).insert(account);
        CredentialHelper.instance(getContext()).insert(credential);
        Toast.makeText(getContext(), "Account successfully registered!", Toast.LENGTH_SHORT).show();
    }

    private void proceed() {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.authentication_container, new LogInFragment())
                .commit();
    }

    private boolean fieldsAreValid() {
        boolean fieldsAreValid = true;
        if(fullName.getText().toString().isEmpty()){
            fullName.setError("Field is required!");
            fieldsAreValid = false;
        }
        if(email.getText().toString().isEmpty()){
            email.setError("Field is required!");
            fieldsAreValid = false;
        }
        if(username.getText().toString().isEmpty()){
            username.setError("Field is required!");
            fieldsAreValid = false;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Field is required!");
            fieldsAreValid = false;
        }
        if(!password.getText().toString().equals(password.getText().toString())){
            password.setError("Passwords does not match!");
            fieldsAreValid = false;
        }
        return fieldsAreValid;
    }
}
