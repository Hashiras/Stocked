package com.example.myapplication.Data.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AuthenticationActivity;
import com.example.myapplication.Data.Account;
import com.example.myapplication.Data.Enums.AccountEnum;
import com.example.myapplication.Data.Helper.AccountHelper;
import com.example.myapplication.Data.Helper.ItemHelper;
import com.example.myapplication.Data.Item;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Menu.ModifyFragment;
import com.example.myapplication.R;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<Item> list;
    private MainActivity context;
    public ItemsAdapter(List<Item> list, MainActivity context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder view, final int position) {
        Item item = list.get(position);
        view.getName().setText("Name: " + item.getName());
        view.getQuantity().setText("Quantity: " + item.getQuantity());
        view.getOwner().setText("Owner: " + AccountHelper.instance(context).get(item.getOwnerID()).getFullName());
        view.getView().setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Name: " + item.getName() + "\nDescription: " + item.getDescription() + "\nQuantity: " + item.getQuantity())
                    .setPositiveButton("Close", (DialogInterface.OnClickListener) (dialog, id) -> {}).create().show();
        });


        view.getUpdate().setOnClickListener(v->{
            Bundle args = new Bundle();
            args.putInt("itemID", item.getID());
            ModifyFragment fragment = new ModifyFragment();
            fragment.setArguments(args);
            context.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
        });
        view.getDelete().setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, id) -> {
                        ItemHelper.instance(context).remove(item);
                        list.remove(item);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Successfully removed item!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, id) -> {

                    }).create().show();
        });

        Account account = AccountHelper.instance(context).get(item.getOwnerID());
        if(account.getType() == AccountEnum.USER)
            view.getDelete().setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, quantity, owner;
        private final Button view2, update, delete;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.card_name);
            quantity = view.findViewById(R.id.card_quantity);
            owner = view.findViewById(R.id.card_owner);
            view2 = view.findViewById(R.id.card_view);
            update = view.findViewById(R.id.card_update);
            delete = view.findViewById(R.id.card_delete);
        }

        public Button getView() {
            return view2;
        }

        public Button getUpdate() {
            return update;
        }

        public Button getDelete() {
            return delete;
        }

        public TextView getName() {
            return name;
        }

        public TextView getQuantity() {
            return quantity;
        }

        public TextView getOwner() {
            return owner;
        }
    }
}