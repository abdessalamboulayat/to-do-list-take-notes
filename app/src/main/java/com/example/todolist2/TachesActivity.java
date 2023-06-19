package com.example.todolist2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TachesActivity extends AppCompatActivity {
    // list view pour afficher les tâche
    private ListView listViewtaches;
    // Adaptateur personnalisé: MyAdapter
    private MyAdapter myAdapter;
    // Button pour ajouter une nouvelle tâche
    private Button btnAjouterTache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taches);
        listViewtaches = (ListView) findViewById(R.id.listViewtaches);
        btnAjouterTache = (Button) findViewById(R.id.btnAjouterTache);
        // récupérer la liste des tâches
        // instancier myAdapter
        // associer myAdapter à listViewtaches
        // ajoute eventListener au Button "btnAjouterTache"
        btnAjouterTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TachesActivity.this, FormAjouterTache.class);
                startActivity(intent);
            }
        });
    }
}