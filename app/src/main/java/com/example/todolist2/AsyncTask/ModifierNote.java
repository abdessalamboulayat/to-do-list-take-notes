package com.example.todolist2.AsyncTask;

import android.os.AsyncTask;

import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Note;

public class ModifierNote extends AsyncTask<Note, Void,Void> {
    private MyDatabase database;
    public ModifierNote(MyDatabase database) {
        this.database = database;
    }
    @Override
    protected Void doInBackground(Note... notes) {
        database.getNoteDao().modifierNote(notes[0]);
        return null;
    }
}
