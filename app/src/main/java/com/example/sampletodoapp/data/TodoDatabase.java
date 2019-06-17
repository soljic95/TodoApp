package com.example.sampletodoapp.data;

import android.content.Context;

import com.example.sampletodoapp.model.Todo;
import com.example.sampletodoapp.model.User;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Todo.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    private static final String DB_NAME = "model_database";

    public abstract TodoDao todoDao();

    public static TodoDatabase instance;

    public static synchronized TodoDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                                            TodoDatabase.class, DB_NAME)
                           .fallbackToDestructiveMigration()
                           .build();
        }
        return instance;
    }
}
