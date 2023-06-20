package com.example.todolist2.AsyncTask;

import android.os.AsyncTask;

import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Note;

public class SupprimerNote extends AsyncTask<Note, Note, Void> {
    private MyDatabase database;
    public SupprimerNote(MyDatabase database) {
        this.database = database;
    }
    @Override
    protected Void doInBackground(Note... notes) {
        database.getNoteDao().supprimerNote(notes[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
    }
}
