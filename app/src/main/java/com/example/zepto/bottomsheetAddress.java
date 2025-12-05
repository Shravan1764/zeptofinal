package com.example.zepto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class bottomsheetAddress extends BottomSheetDialogFragment {

    private LinearLayout layoutViewMode, layoutEditMode;
    private TextView tvCurrentAddress;
    private EditText etHouse, etArea, etPincode;
    private Button btnSave, btnEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottomsheet_address, container, false);

        // Initialize Views
        layoutViewMode = view.findViewById(R.id.layout_view_mode);
        layoutEditMode = view.findViewById(R.id.layout_edit_mode);
        tvCurrentAddress = view.findViewById(R.id.tv_current_address);
        etHouse = view.findViewById(R.id.et_house_no);
        etArea = view.findViewById(R.id.et_area);
        etPincode = view.findViewById(R.id.et_pincode);
        btnSave = view.findViewById(R.id.btn_save_address);
        btnEdit = view.findViewById(R.id.btn_edit_address);

        checkExistingAddress();

        btnSave.setOnClickListener(v -> {
            String house = etHouse.getText().toString();
            String area = etArea.getText().toString();
            String pin = etPincode.getText().toString();

            if (house.isEmpty() || area.isEmpty()) {
                Toast.makeText(getContext(), "Please fill details", Toast.LENGTH_SHORT).show();
                return;
            }

            String fullAddress = house + ", " + area + " - " + pin;
            saveAddress(fullAddress);
        });

        btnEdit.setOnClickListener(v -> {
            layoutViewMode.setVisibility(View.GONE);
            layoutEditMode.setVisibility(View.VISIBLE);
        });

        return view;
    }

    private void checkExistingAddress() {
        SharedPreferences prefs = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        String savedAddress = prefs.getString("UserAddress", null);

        if (savedAddress != null) {

            tvCurrentAddress.setText(savedAddress);
            layoutViewMode.setVisibility(View.VISIBLE);
            layoutEditMode.setVisibility(View.GONE);
        } else {

            layoutViewMode.setVisibility(View.GONE);
            layoutEditMode.setVisibility(View.VISIBLE);
        }
    }

    private void saveAddress(String address) {

        SharedPreferences prefs = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        prefs.edit().putString("UserAddress", address).apply();


        tvCurrentAddress.setText(address);
        layoutViewMode.setVisibility(View.VISIBLE);
        layoutEditMode.setVisibility(View.GONE);

        Toast.makeText(getContext(), "Address Saved!", Toast.LENGTH_SHORT).show();

    }
}
