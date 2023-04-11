package com.example.myapplication.Menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.Account;
import com.example.myapplication.Data.Adapter.ItemsAdapter;
import com.example.myapplication.Data.Enums.AccountEnum;
import com.example.myapplication.Data.Helper.AccountHelper;
import com.example.myapplication.Data.Helper.ItemHelper;
import com.example.myapplication.Data.Item;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    public ListFragment() {
        super(R.layout.fragment_list);
    }

    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;

    private EditText search;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        search = view.findViewById(R.id.list_search);
        recyclerView = view.findViewById(R.id.list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sharedPreferences = ((MainActivity) getActivity()).getSharedPreferences();

        populateList();

        view.findViewById(R.id.button4).setOnClickListener(v->{
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, new AddFragment())
                    .commit();
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(search.getText().toString().isEmpty()){
                    populateList();
                    return;
                }
                List<Item> list = new ArrayList<>();
                for(Item item: ItemHelper.instance(getContext()).get()){
                    if(item.getName().contains(search.getText().toString()))
                        list.add(item);
                }
                ItemsAdapter itemsAdapter = new ItemsAdapter(list, (MainActivity) getActivity());
                recyclerView.setAdapter(itemsAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
                return;
            }
        });
    }

    private void populateList(){
        Account account = AccountHelper.instance(getContext()).get(sharedPreferences.getInt("accountID", 0));
        ItemsAdapter itemsAdapter;
        if(account.getType() == AccountEnum.ADMIN)
            itemsAdapter = new ItemsAdapter(ItemHelper.instance(getContext()).get(), (MainActivity) getActivity());
        else{
            List<Item> list = new ArrayList<>();
            for (Item item : ItemHelper.instance(getContext()).get()){
                if(account.getID() == item.getOwnerID())
                    list.add(item);
            }
            itemsAdapter = new ItemsAdapter(list, (MainActivity) getActivity());
        }
        recyclerView.setAdapter(itemsAdapter);
    }
}
