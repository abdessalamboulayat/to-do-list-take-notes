package com.example.todolist2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolist2.AsyncTask.AjouterNote;
import com.example.todolist2.AsyncTask.ModifierNote;
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
        getSupportActionBar().hide();
        setContentView(R.layout.activity_form_ajouter_note);
        txtTitreNote = (EditText) findViewById(R.id.txtTitreNote);
        txtDescriptionNote = (EditText) findViewById(R.id.txtDescriptionNote);
        btnAjouterNote = (Button) findViewById(R.id.btnAjouterNote);
        // crée une instance de la base de données
        database = MyDatabase.getInstance(this);
        // vérifier s'il y a des paramétres extra
        Bundle extra = this.getIntent().getExtras();
        if(extra == null){
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
        else {
            btnAjouterNote.setText("Modifier");
            txtTitreNote.setText(extra.getString("titreNote"));
            txtDescriptionNote.setText(extra.getString("descriptionNote"));
            Long idNote = extra.getLong("idNote");
            btnAjouterNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LiveData<Note> note = database.getNoteDao().getNoteById(idNote);
                    note.observe(FormAjouterNote.this, new Observer<Note>() {
                        @Override
                        public void onChanged(Note note) {
                            note.setTitre(txtTitreNote.getText().toString());
                            note.setDescription(txtDescriptionNote.getText().toString());
                            new ModifierNote(database).execute(note);
                            finish();
                            Toast.makeText(FormAjouterNote.this,"Note modifée",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }
}