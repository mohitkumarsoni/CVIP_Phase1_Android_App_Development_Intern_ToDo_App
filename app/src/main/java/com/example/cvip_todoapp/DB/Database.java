package com.example.cvip_todoapp.DB;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cvip_todoapp.model_entity.Todo;

@androidx.room.Database(entities = Todo.class, version = 1)
public abstract class Database extends RoomDatabase {

    private static final String DATABASE_NAME = "Todo_app_DB";
    private static Database dbInstance;

    public static synchronized Database getInstance(Context context){
        if (dbInstance == null){
            dbInstance = Room.databaseBuilder(context, Database.class, DATABASE_NAME).
                    fallbackToDestructiveMigration()
                    .build();
        }
        return dbInstance;
    }

    public abstract Dao dao();

}
