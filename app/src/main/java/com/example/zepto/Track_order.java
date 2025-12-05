package com.example.zepto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class Track_order extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrackingAdapter adapter;
    TextView tvItemsList, tvTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        tvItemsList = findViewById(R.id.tv_track_order_items);
        tvTotalPrice = findViewById(R.id.tv_track_order_total);

        AppDatabase db = AppDatabase.getDbInstance(this);

        recyclerView = findViewById(R.id.rv_tracking_timeline);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> trackingSteps = Arrays.asList(
                "Order Placed",
                "Packed",
                "Shipped",
                "Out for Delivery",
                "Delivered"
        );

        String currentStatusFromBackend = "PACKED";
        int currentStepIndex = getStatusIndex(currentStatusFromBackend);
        adapter = new TrackingAdapter(trackingSteps, currentStepIndex);
        recyclerView.setAdapter(adapter);

        db.cartDao().getAllItems().observe(this, new Observer<List<databaseitem>>() {
            @Override
            public void onChanged(List<databaseitem> cartItems) {

                if (cartItems == null || cartItems.isEmpty()) {
                    tvItemsList.setText("No items found.");
                    return;
                }
                StringBuilder builder = new StringBuilder();
                int totalAmount = 0;
                for (databaseitem item : cartItems) {
                    builder.append("• ").append(item.name).append("\n");
                    try {
                        String cleanPrice = item.price.replace("₹", "").trim();
                        totalAmount += Integer.parseInt(cleanPrice);
                    } catch (Exception e) {}
                }
                tvItemsList.setText(builder.toString());
                tvTotalPrice.setText("Total: ₹" + totalAmount);
            }
        });
    }
    private int getStatusIndex(String status) {
        switch (status.toUpperCase()) {
            case "PLACED": return 0;
            case "PACKED": return 1;
            case "SHIPPED": return 2;
            case "OUT_FOR_DELIVERY": return 3;
            case "DELIVERED": return 4;
            default: return 0;
        }

    }
}

