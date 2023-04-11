package com.example.myapplication.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Data.Account;
import com.example.myapplication.Data.Helper.AccountHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Menu.AccountSetup.AccountSetUpFragment;
import com.example.myapplication.AuthenticationActivity;
import com.example.myapplication.Menu.AccountSetup.PasswordFragment;
import com.example.myapplication.R;

public class AccountFragment extends Fragment {
    private Button update, password, sign_out;
    public AccountFragment() {
        super(R.layout.fragment_account);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        update = view.findViewById(R.id.account_update);
        update.setOnClickListener(v->{
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new AccountSetUpFragment())
                    .commit();
        });
        password = view.findViewById(R.id.account_password);
        password.setOnClickListener(v->{
            new PasswordFragment().show(getChildFragmentManager(), "Password Dialog");
        });
        sign_out = view.findViewById(R.id.account_signout);
        sign_out.setOnClickListener(v->{
            SharedPreferences sharedPreferences = ((MainActivity) getActivity()).getSharedPreferences();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("accountID", 0);
            editor.commit();
            getActivity().finish();
            startActivity(new Intent(getActivity(), AuthenticationActivity.class));
        });

        TextView fullname = view.findViewById(R.id.account_fullname);
        TextView email = view.findViewById(R.id.account_email);

        SharedPreferences sharedPreferences = ((MainActivity) getActivity()).getSharedPreferences();
        Account account = AccountHelper.instance(getContext()).get(sharedPreferences.getInt("accountID", 0));
        fullname.setText(account.getFullName());
        email.setText(account.getEmail());

    }
}
