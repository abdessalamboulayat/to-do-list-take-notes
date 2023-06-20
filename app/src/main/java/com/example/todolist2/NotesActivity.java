package com.example.todolist2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Note;

import java.util.Collections;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 200;
    // list view pour afficher les notes
    private ListView listViewNotes;
    // le button qui permet de supprimer une note
    private ImageButton btnSupprimerNote;
    // Adaptateur personnalisé: MyAdapter
    private MyAdapter myAdapter;
    // Button pour ajouter une nouvelle note
    private Button btnAjouterNote;
    private MyDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_notes);

        listViewNotes = (ListView) findViewById(R.id.listViewNotes);
        ImageButton btnAjouterNote = (ImageButton) findViewById(R.id.btnAjouterNote);
        // crée une instance de la base de données
        database = MyDatabase.getInstance(this);
        // Récupérer la liste des tâches
        LiveData<List<Note>> notes = recupererLesNotes(database);
        // boite de dialogue qui s'affiche à l'utilisateur quand il essaye de supprimer une note
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // instancier l'adaptateur
        //myAdapter = new MyAdapter(this,notes);
        notes.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                myAdapter = new MyAdapter(NotesActivity.this, notes, database);
                // associer l'adaptateur à listView
                listViewNotes.setAdapter(myAdapter);
                listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Note note = (Note) listViewNotes.getItemAtPosition(position);
                        System.out.println("---- titre : "+note.getTitre());
                        Toast.makeText(NotesActivity.this,"element"+note.getTitre(),Toast.LENGTH_SHORT).show();
                        showDialog(NotesActivity.this,"","Voulez-vous supprimer ou modifier la note?");
                    }
                });
            }
        });
        btnAjouterNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, FormAjouterNote.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // On affiche Toast si la nouvelle note est ajoutée
            Toast.makeText(NotesActivity.this, "Note ajoutée avec succès", Toast.LENGTH_SHORT).show();
        }
    }
    public LiveData<List<Note>> recupererLesNotes(MyDatabase database){
        return database.getNoteDao().recupererNotes();
    }
    public static void showDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}