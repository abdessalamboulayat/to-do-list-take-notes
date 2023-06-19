package com.example.todolist2.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todolist2.Dao.NoteDao;
import com.example.todolist2.Entity.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract NoteDao getNoteDao();
    public static final String DB_NAME = "db_app";
    private static MyDatabase instance;
    public static MyDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MyDatabase.class,
                    DB_NAME
            ).build();
        }
        return instance;
    }
}
