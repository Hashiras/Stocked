package com.example.myapplication.Menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Data.Account;
import com.example.myapplication.Data.Helper.AccountHelper;
import com.example.myapplication.Data.Helper.ItemHelper;
import com.example.myapplication.Data.Item;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class AddFragment extends Fragment {
    public AddFragment() {
        super(R.layout.fragment_item_setup);
    }
    private SharedPreferences sharedPreferences;

    private EditText name, desc, quantity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.setup_name);
        desc = view.findViewById(R.id.setup_desc);
        quantity = view.findViewById(R.id.setup_quantity);
        sharedPreferences = ((MainActivity) getActivity()).getSharedPreferences();

        ((TextView) view.findViewById(R.id.set_up_operation)).setText("Add Item");

        Button button = view.findViewById(R.id.button3);
        button.setOnClickListener(v -> {
            if (fieldsAreValid())
                setUpItem();
        });
    }

    private boolean fieldsAreValid() {
        boolean fieldsAreValid = true;
        if (name.getText().toString().isEmpty()) {
            name.setError("Field is required");
            fieldsAreValid = false;
        }
        if (desc.getText().toString().isEmpty()) {
            desc.setError("Field is required");
            fieldsAreValid = false;
        }
        if (quantity.getText().toString().isEmpty()) {
            quantity.setError("Field is required");
            fieldsAreValid = false;
        }
        return fieldsAreValid;
    }

    private void setUpItem(){
        Item item = new Item();
        item.setName(name.getText().toString());
        item.setDescription(desc.getText().toString());
        item.setQuantity(Integer.valueOf(quantity.getText().toString()));
        item.setOwnerID(sharedPreferences.getInt("accountID", 0));
        ItemHelper.instance(getContext()).insert(item);

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, new ListFragment())
                .commit();
    }
}
