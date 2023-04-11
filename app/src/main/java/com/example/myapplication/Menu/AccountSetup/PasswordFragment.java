package com.example.myapplication.Menu.AccountSetup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Data.Credential;
import com.example.myapplication.Data.Helper.AccountHelper;
import com.example.myapplication.Data.Helper.CredentialHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Menu.AccountFragment;
import com.example.myapplication.R;

public class PasswordFragment extends DialogFragment {
    public PasswordFragment() {
        super(R.layout.dialog_password);
    }

    private EditText oldPassword, password1, password2;
    private Credential credential;
    private SharedPreferences sharedPreferences;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oldPassword = view.findViewById(R.id.password_old);
        password1 = view.findViewById(R.id.password_new);
        password2 = view.findViewById(R.id.password_confirm);
        sharedPreferences = ((MainActivity) getActivity()).getSharedPreferences();
        credential = CredentialHelper.instance(getContext()).get(
                AccountHelper.instance(getContext()).get(
                        sharedPreferences.getInt("accountID", 0))
                        .getCredentialID());

        Button button = view.findViewById(R.id.button6);
        button.setOnClickListener(v->{
            if (fieldsAreValid())
                updateAccount();
        });
    }

    private boolean fieldsAreValid() {
        boolean fieldsAreValid = true;
        if (oldPassword.getText().toString().isEmpty()) {
            oldPassword.setError("Field is required");
            fieldsAreValid = false;
        }
        if (password1.getText().toString().isEmpty()) {
            password1.setError("Field is required");
            fieldsAreValid = false;
        }
        if (!password1.getText().toString().equals(password2.getText().toString())) {
            password2.setError("Passwords does not match!");
            fieldsAreValid = false;
        }
        return fieldsAreValid;
    }

    private void updateAccount(){
        credential.setPassword(password1.getText().toString());
        CredentialHelper.instance(getContext()).update(credential);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new AccountFragment())
                .commit();
    }
}