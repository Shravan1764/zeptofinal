package com.example.zepto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView trackOrderTextView = findViewById(R.id.track_order);


        trackOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsActivity.this, Track_order.class);

                intent.putExtra("ORDER_STATUS", "SHIPPED");

                startActivity(intent);
            }
        });

        TextView address = findViewById(R.id.addressSetings);

        address.setOnClickListener(v -> {
            bottomsheetAddress sheet = new bottomsheetAddress();
            sheet.show(getSupportFragmentManager(), "AddressSheet");
        });

    }
}