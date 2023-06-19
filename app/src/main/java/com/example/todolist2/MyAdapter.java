package com.example.todolist2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.todolist2.Entity.Note;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Note> {
    private LayoutInflater inflater;
    public MyAdapter(Context context, List<Note> notes) {
        super(context, 0, notes);
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
        // Associe les attributs au vue
        titreNote.setText(note.getTitre());
        descriptionNote.setText(note.getDescription());
        return convertView;
    }
}