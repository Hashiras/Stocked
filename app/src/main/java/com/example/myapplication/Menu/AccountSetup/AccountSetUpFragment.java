package com.example.myapplication.Menu.AccountSetup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Data.Account;
import com.example.myapplication.Data.Helper.AccountHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Menu.AccountFragment;
import com.example.myapplication.R;

public class AccountSetUpFragment extends Fragment {
    public AccountSetUpFragment() {
        super(R.layout.fragment_setup_account);
    }

    private SharedPreferences sharedPreferences;
    private Account account;
    private EditText fullName, email;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fullName = view.findViewById(R.id.modify_fullname);
        email = view.findViewById(R.id.modify_email);
        Button button = view.findViewById(R.id.button5);
        sharedPreferences = ((MainActivity) getActivity()).getSharedPreferences();
        account = AccountHelper.instance(getContext()).get(sharedPreferences.getInt("accountID", 0));

        Log.i("CredentialChecker", "Viewing account ID: " + sharedPreferences.getInt("accountID", 0));

        init();

        button.setOnClickListener(v->{
            if (fieldsAreValid())
                updateAccount();
        });
    }

    private boolean fieldsAreValid() {
        Log.i("FormChecker", "Run!");
        boolean fieldsAreValid = true;
        if (fullName.getText().toString().isEmpty()) {
            fullName.setError("Field is required");
            fieldsAreValid = false;
        }
        if (email.getText().toString().isEmpty()) {
            email.setError("Field is required");
            fieldsAreValid = false;
        }
        return fieldsAreValid;
    }

    private void updateAccount(){
        account.setFullName(fullName.getText().toString());
        account.setEmail(email.getText().toString());
        AccountHelper.instance(getContext()).update(account);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new AccountFragment())
                .commit();
    }

    private void init(){
        fullName.setText(account.getFullName());
        email.setText(account.getEmail());
    }
}
