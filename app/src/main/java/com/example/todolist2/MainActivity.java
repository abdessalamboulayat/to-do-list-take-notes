package com.example.todolist2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // Button qui permet à l'utilisateur d'afficher ses tâches
    private Button btnAfficherTaches;
    // Button qui permet à l'utilisateur d'afficher ses notes
    private Button btnAfficherNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAfficherTaches = (Button) findViewById(R.id.btnAfficherTaches);
        btnAfficherNotes = (Button) findViewById(R.id.btnAfficherNotes);
        // ajouter eventListner au btnAfficherTaches pour afficher les tâches
        btnAfficherTaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TachesActivity.class);
                startActivity(intent);
            }
        });
        // ajouter eventListner au btnAfficherNotes pour afficher les notes
        btnAfficherNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });
    }
}