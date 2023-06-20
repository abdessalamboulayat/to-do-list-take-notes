package com.example.todolist2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;

import com.example.todolist2.AsyncTask.SupprimerNote;
import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Note;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Note> {
    private LayoutInflater inflater;
    private MyDatabase database;
    public MyAdapter(Context context, List<Note> notes, MyDatabase database) {
        super(context, 0, notes);
        this.database = database;
        inflater = LayoutInflater.from(context);
    }
    /*public MyAdapter(Context context, List<Note> notes) {
        super(context, 0, notes);
        inflater = LayoutInflater.from(context);
    }*/
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.note_layout, parent, false);
        }
        // Obtenez l'objet pour la position donnée
        Note note = (Note) getItem(position);
        // Associez les attributs de l'objet aux vues de l'élément de la liste
        TextView titreNote = convertView.findViewById(R.id.titreNote);
        TextView descriptionNote = convertView.findViewById(R.id.descriptionNote);
        // le button qui permet de supprimer une note
        ImageButton btnSupprimerNote = (ImageButton) convertView.findViewById(R.id.btnSupprimerNote);
        // le button qui permet de modifier une note
        ImageButton btnModifierNote = (ImageButton) convertView.findViewById(R.id.btnModifierNote);
        btnSupprimerNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(getContext(),"","Voulez-vous supprimer ou modifier la note?", database, note);
            }
        });
        btnModifierNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FormAjouterNote.class);
                intent.putExtra("titreNote", note.getTitre());
                intent.putExtra("descriptionNote", note.getDescription());
                intent.putExtra("idNote", note.getId());
                getContext().startActivity(intent);
            }
        });
        // Associe les attributs au vue
        titreNote.setText(note.getTitre());
        descriptionNote.setText(note.getDescription());
        return convertView;
    }
    public static void showDialog(Context context, String title, String message, MyDatabase database, Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SupprimerNote(database).execute(note);
                System.out.println("--- NOTE SUPPRIMER ....");
                dialog.dismiss();
            }
        });
        builder.show();
    }
}