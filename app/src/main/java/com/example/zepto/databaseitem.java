package com.example.zepto;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "cart_table")
public class databaseitem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String price;
    public int imageResId;

    public databaseitem(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }
}
