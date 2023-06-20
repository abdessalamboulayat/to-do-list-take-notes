package com.example.todolist2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Note;
import com.example.todolist2.Entity.Tache;

import java.util.List;

public class TachesActivity extends AppCompatActivity {
    // list view pour afficher les tâche
    private ListView listViewtaches;
    // Adaptateur personnalisé: MyAdapter
    private AdapterTache myAdapter;
    // Button pour ajouter une nouvelle tâche
    private ImageButton btnAjouterTache;
    private MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_taches);

        listViewtaches = (ListView) findViewById(R.id.listViewtaches);
        btnAjouterTache = (ImageButton) findViewById(R.id.btnAjouterTache);
        // instance de la base de données
        database = MyDatabase.getInstance(TachesActivity.this);
        // récupérer la liste des tâches
        LiveData<List<Tache>> taches = recupererLesTaches(database);
        taches.observe(this, new Observer<List<Tache>>() {
            @Override
            public void onChanged(List<Tache> taches) {
                // instancier myAdapter
                myAdapter = new AdapterTache(TachesActivity.this, taches, database);
                // associer l'adaptateur à listView
                listViewtaches.setAdapter(myAdapter);
                listViewtaches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Note note = (Note) listViewtaches.getItemAtPosition(position);
                        System.out.println("---- titre : "+note.getTitre());
                        Toast.makeText(TachesActivity.this,"element"+note.getTitre(),Toast.LENGTH_SHORT).show();
                        //showDialog(TachesActivity.this,"","Voulez-vous supprimer ou modifier la note?");
                    }
                });
            }
        });
        // ajoute eventListener au Button "btnAjouterTache"
        btnAjouterTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TachesActivity.this, FormAjouterTache.class);
                startActivity(intent);
            }
        });
    }
    /**
     * cette fonction nous permet de récupérer la liste des tâches
     * @param database
     * @return
     */
    public LiveData<List<Tache>> recupererLesTaches(MyDatabase database){
        return database.getTacheDao().recupererLesTaches();
    }
}