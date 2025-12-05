package com.example.zepto;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {databaseitem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "zepto_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}

