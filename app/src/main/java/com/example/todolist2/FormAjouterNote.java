package com.example.todolist2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todolist2.AsyncTask.AjouterNote;
import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Note;

public class FormAjouterNote extends AppCompatActivity {
    private EditText txtTitreNote;
    private EditText txtDescriptionNote;
    private Button btnAjouterNote;
    private MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_ajouter_note);
        txtTitreNote = (EditText) findViewById(R.id.txtTitreNote);
        txtDescriptionNote = (EditText) findViewById(R.id.txtDescriptionNote);
        btnAjouterNote = (Button) findViewById(R.id.btnAjouterNote);
        // crée une instance de la base de données
        database = MyDatabase.getInstance(this);
        btnAjouterNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titreNote = txtTitreNote.getText().toString();
                String descriptionNote = txtDescriptionNote.getText().toString();
                // instancier une nouvelle note
                Note note = new Note(titreNote, descriptionNote);
                // executer asyncTask ajouterNote
                new AjouterNote(database).execute(note);
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}