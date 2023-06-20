package com.example.todolist2.AsyncTask;

import android.os.AsyncTask;

import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Tache;

public class SupprimerTache extends AsyncTask<Tache, Void, Void> {
    private MyDatabase database;
    public SupprimerTache(MyDatabase database) {
        this.database = database;
    }
    @Override
    protected Void doInBackground(Tache... taches) {
        database.getTacheDao().supprimerTache(taches[0]);
        return null;
    }
}
