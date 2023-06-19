package com.example.todolist2.AsyncTask;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Note;

import java.util.List;

public class RecupererNotes extends AsyncTask<Void, Void, LiveData<List<Note>>> {
    private MyDatabase database;
    public RecupererNotes(MyDatabase database) {
        this.database = database;
    }
    @Override
    protected LiveData<List<Note>> doInBackground(Void... voids) {
        return database.getNoteDao().recupererNotes();
    }

    @Override
    protected void onPostExecute(LiveData<List<Note>> notes) {
        super.onPostExecute(notes);
    }
}
