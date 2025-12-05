package com.example.zepto;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvTotalPrice;
    private Button btnCheckout;
    private CartAdapter cartAdapter;
    private List<Product> cartItems;
    private int currentTotalAmount = 0;
    private List<databaseitem> dbCartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        tvTotalPrice = findViewById(R.id.total_price);
        btnCheckout = findViewById(R.id.btn_checkout);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, dbCartList); // Adapter now uses DB list
        recyclerView.setAdapter(cartAdapter);

        saveIncomingItemsToDatabase();
        btnCheckout.setOnClickListener(v -> {
            if (dbCartList == null || dbCartList.isEmpty()) {
                Toast.makeText(CartActivity.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentTotalAmount <= 0) {
                Toast.makeText(CartActivity.this, "Add items to cart first", Toast.LENGTH_SHORT).show();
                return;
            }
            showOrderPlacedNotification();
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            intent.putExtra("TOTAL_AMOUNT", currentTotalAmount);
            startActivity(intent);
        });


        AppDatabase db = AppDatabase.getDbInstance(this);
        db.cartDao().getAllItems().observe(this, new Observer<List<databaseitem>>() {
            @Override
            public void onChanged(List<databaseitem> items) {
                dbCartList.clear();
                dbCartList.addAll(items);

                cartAdapter.notifyDataSetChanged();
                calculateTotal();
            }
        });
    }
    // Add this method to CartActivity.java
    private void showOrderPlacedNotification() {
        String channelId = "zepto_orders";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 1. Create Channel (Required for Android 8+)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Order Status",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        // 2. Build Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your icon
                .setContentTitle("Order Placed Successfully!")
                .setContentText("Your order for ₹" + currentTotalAmount + " is being processed.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // 3. Show It
        try {
            notificationManager.notify(1, builder.build());
        } catch (SecurityException e) {
            // Handle permission not granted case
            e.printStackTrace();
        }
    }


    private void saveIncomingItemsToDatabase() {

        List<Product> temporaryList = CartManager.getInstance().getCartItems();

        if (temporaryList != null && !temporaryList.isEmpty()) {
            AppDatabase db = AppDatabase.getDbInstance(this);

            new Thread(() -> {

                for (Product p : temporaryList) {
                    databaseitem item = new databaseitem(p.getName(), p.getPrice(), p.getImageResId());
                    db.cartDao().insertItem(item);
                }

                CartManager.getInstance().clear();

            }).start();
        }
    }
    private void calculateTotal() {
        int total = 0;


        for (databaseitem item : dbCartList) {
            try {

                String cleanPrice = item.price.replace("₹", "").trim();

                int priceValue = Integer.parseInt(cleanPrice);


                total += priceValue;
            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
        }


        currentTotalAmount = total;

        tvTotalPrice.setText("Total: ₹" + total);
    }
}
