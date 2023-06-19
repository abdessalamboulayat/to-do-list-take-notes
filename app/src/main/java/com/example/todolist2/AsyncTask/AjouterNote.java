package com.example.todolist2.AsyncTask;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Note;

public class AjouterNote extends AsyncTask<Note,Void, Long> {
    private MyDatabase database;
    public AjouterNote(MyDatabase database){
        this.database = database;
    }
    @Override
    protected Long doInBackground(Note... notes) {
        Long result = database.getNoteDao().ajouterNote(notes[0]);
        publishProgress();
        return result;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        System.out.println("- Nouvelle note est ajoutée ...");
        // Toast.makeText(, "Nouvelle note est ajoutée", Toast.LENGTH_LONG);
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
    }
}
