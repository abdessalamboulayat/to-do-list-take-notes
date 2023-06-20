package com.example.todolist2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.todolist2.AsyncTask.ModifierTache;
import com.example.todolist2.AsyncTask.SupprimerNote;
import com.example.todolist2.AsyncTask.SupprimerTache;
import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Note;
import com.example.todolist2.Entity.Tache;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterTache extends ArrayAdapter<Tache> {
    private LayoutInflater inflater;
    private MyDatabase database;
    // button qui permet de modifier la tache
    private ImageButton btnModifierTache;
    // button qui permet de supprimer une tache
    private ImageButton btnSupprimerTache;
    public AdapterTache(Context context, List<Tache> taches, MyDatabase database) {
        super(context, 0, taches);
        this.database = database;
        inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tache_layout, parent, false);
        }
        // Obtenez l'objet pour la position donnée
        Tache tache = (Tache) getItem(position);
        // Associez les attributs de l'objet aux vues de l'élément de la liste
        TextView titreTache = convertView.findViewById(R.id.txtTitreTache);
        TextView descriptionTache = convertView.findViewById(R.id.txtDescriptionTache);
        TextView dateDebut = convertView.findViewById(R.id.dateDebut);
        TextView dateFin = convertView.findViewById(R.id.dateFin);
        CheckBox checkboxTache = convertView.findViewById(R.id.checkboxTache);
        checkboxTache.setChecked(tache.isTermine());
        // le button qui permet de modifier une tache
        btnModifierTache = (ImageButton) convertView.findViewById(R.id.btnModifierTache);
        // le button qui permet de supprimer une tache
        btnSupprimerTache = (ImageButton) convertView.findViewById(R.id.btnSupprimerTache);
        // le button qui permet de modifier une note

        //ImageButton btnModifierNote = (ImageButton) convertView.findViewById(R.id.btnModifierNote);
        btnSupprimerTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(getContext(),"","Voulez-vous supprimer ou modifier la note?", database, tache);
            }
        });
        btnModifierTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FormAjouterTache.class);
                intent.putExtra("titreTache", tache.getTitreTache());
                intent.putExtra("descriptionTache", tache.getDescriptionTache());
                intent.putExtra("dateDebut", String.valueOf(tache.getDateDebut()));
                intent.putExtra("dateFin", String.valueOf(tache.getDateFin()));
                intent.putExtra("idTache", tache.getId());
                getContext().startActivity(intent);
            }
        });
        checkboxTache.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                System.out.println("--- is checked true "+isChecked);
                tache.setTermine(isChecked);
                new ModifierTache(database).execute(tache);
            }
        });
        // Associe les attributs au vue
        titreTache.setText(tache.getTitreTache());
        descriptionTache.setText(tache.getDescriptionTache());
        // convertire les dates de debut et de fin de millisecondes en heures et minutes
        String dateDebut1 = convertirDateEnHeureMinute(tache.getDateDebut());
        String dateFin1 = convertirDateEnHeureMinute(tache.getDateFin());
        dateDebut.setText("Début : "+dateDebut1);
        dateFin.setText("Fin : "+dateFin1);
        return convertView;
    }
    public static void showDialog(Context context, String title, String message, MyDatabase database, Tache tache) {
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
                new SupprimerTache(database).execute(tache);
                System.out.println("--- Tache SUPPRIMER ....");
                dialog.dismiss();
            }
        });
        builder.show();
    }
    public String convertirDateEnHeureMinute(long tempsEnMillisecondes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(tempsEnMillisecondes);

        int heures = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        String heureMinute = String.format(Locale.getDefault(), "%02d:%02d", heures, minutes);
        return heureMinute;
    }
}
