package com.example.zepto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; // Added for feedback

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<databaseitem> cartItems;

    public CartAdapter(Context context, List<databaseitem> cartItems){
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position){
        // Get the item from the database list
        databaseitem item = cartItems.get(position);

        holder.tvProductName.setText(item.name);
        holder.tvProductPrice.setText(item.price);
        holder.productImage.setImageResource(item.imageResId);


        holder.btnRemove.setOnClickListener(v -> {

            AppDatabase db = AppDatabase.getDbInstance(context);

            new Thread(() -> {
                db.cartDao().delete(item);

                ((android.app.Activity) context).runOnUiThread(() ->
                        Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show()
                );
            }).start();
        });
    }

    @Override
    public int getItemCount(){
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView tvProductName, tvProductPrice;
        Button btnRemove;

        public CartViewHolder(@NonNull View itemView){
            super((itemView));
            productImage = itemView.findViewById(R.id.cart_product_image);
            tvProductName = itemView.findViewById(R.id.cart_product_name);
            tvProductPrice = itemView.findViewById(R.id.cart_product_price);
            btnRemove = itemView.findViewById(R.id.remove_from_cart);
        }
    }
}
