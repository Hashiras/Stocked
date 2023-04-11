package com.example.myapplication.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class PinFragment extends Fragment {


    private static final int MAX_PIN_LENGTH = 4;
    private int[] mPin;
    private int mPinLength;
    private TextView mPinTextView;
    public PinFragment() {
        super(R.layout.fragment_pin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPinTextView = view.findViewById(R.id.pin_view);
        mPin = new int[MAX_PIN_LENGTH];
        mPinLength = 0;
        updatePinTextView();

        view.findViewById(R.id.button_1).setOnClickListener(this::onNumberButtonClick);
        view.findViewById(R.id.button_2).setOnClickListener(this::onNumberButtonClick);
        view.findViewById(R.id.button_3).setOnClickListener(this::onNumberButtonClick);
        view.findViewById(R.id.button_4).setOnClickListener(this::onNumberButtonClick);
        view.findViewById(R.id.button_5).setOnClickListener(this::onNumberButtonClick);
        view.findViewById(R.id.button_6).setOnClickListener(this::onNumberButtonClick);
        view.findViewById(R.id.button_7).setOnClickListener(this::onNumberButtonClick);
        view.findViewById(R.id.button_8).setOnClickListener(this::onNumberButtonClick);
        view.findViewById(R.id.button_9).setOnClickListener(this::onNumberButtonClick);
        view.findViewById(R.id.button_0).setOnClickListener(this::onNumberButtonClick);
        view.findViewById(R.id.button_backspace).setOnClickListener(this::onBackspaceButtonClick);
        view.findViewById(R.id.button_clear).setOnClickListener(this::onClearButtonClick);


    }

    public void onNumberButtonClick(View view) {
        Button button = (Button) view;
        int number = Integer.parseInt(button.getText().toString());
        addNumberToPin(number);
    }

    public void onClearButtonClick(View view) {
        clearPin();
    }

    public void onBackspaceButtonClick(View view) {
        removeLastNumberFromPin();
    }


    private void addNumberToPin(int number) {
        if (mPinLength < MAX_PIN_LENGTH) {
            mPin[mPinLength++] = number;
            updatePinTextView();
        }
        updatePinTextView();

        if (isPinComplete()) {
            getActivity().finish();
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
        else{
            Toast.makeText(getActivity(), "Incorrect Password", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearPin() {
        for (int i = 0; i < mPin.length; i++) {
            mPin[i] = 0;
        }
        mPinLength = 0;
        updatePinTextView();
    }

    private void removeLastNumberFromPin() {
        if (mPinLength > 0) {
            mPin[--mPinLength] = 0;
            updatePinTextView();
        }
    }

    private boolean isPinComplete() {
        for (int i = 0; i < MAX_PIN_LENGTH; i++) {
            if (mPin[i] == -1) {
                return false;
            }
        }
        return true;
    }

    private void updatePinTextView() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MAX_PIN_LENGTH; i++) {
            if (i < mPinLength) {
                builder.append("\u25CF "); // Unicode character for a bullet point
            } else {
                builder.append("\u25CB ");
            }
        }
        mPinTextView.setText(builder.toString());
    }
}
