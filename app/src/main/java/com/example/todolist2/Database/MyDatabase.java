package com.example.todolist2.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todolist2.Dao.NoteDao;
import com.example.todolist2.Dao.TacheDao;
import com.example.todolist2.Entity.Note;
import com.example.todolist2.Entity.Tache;

@Database(entities = {Note.class, Tache.class}, version = 4)
//@TypeConverters({ConvertersDate.class})
public abstract class MyDatabase extends RoomDatabase {
    public abstract NoteDao getNoteDao();
    public abstract TacheDao getTacheDao();
    public static final String DB_NAME = "db_app";
    private static MyDatabase instance;
    public static MyDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MyDatabase.class,
                    DB_NAME
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
