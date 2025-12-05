package com.example.zepto;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_page); // You need to create this XML

        ImageView imgProduct = findViewById(R.id.detail_image);
        TextView tvName = findViewById(R.id.detail_name);
        TextView tvPrice = findViewById(R.id.detail_price);
        TextView tvDescription = findViewById(R.id.detail_description);

        // 1. Get Data from Intent
        String name = getIntent().getStringExtra("NAME");
        String price = getIntent().getStringExtra("PRICE");
        int imageRes = getIntent().getIntExtra("IMAGE", 0);
        String desc = getIntent().getStringExtra("DESC"); // Add description to your Product model

        // 2. Set Data
        tvName.setText(name);
        tvPrice.setText(price);
        imgProduct.setImageResource(imageRes);
        tvDescription.setText(desc != null ? desc : "No description available.");
    }
}












