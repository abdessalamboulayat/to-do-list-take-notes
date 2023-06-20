package com.example.todolist2.AsyncTask;

import android.os.AsyncTask;

import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Tache;

public class AjouterTache extends AsyncTask<Tache, Void, Void> {
    private MyDatabase database;

    public AjouterTache(MyDatabase database) {
        this.database = database;
    }

    @Override
    protected Void doInBackground(Tache... taches) {
        database.getTacheDao().ajouterTache(taches[0]);
        return null;
    }
}
