package com.example.todolist2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolist2.AsyncTask.AjouterTache;
import com.example.todolist2.AsyncTask.ModifierTache;
import com.example.todolist2.Database.MyDatabase;
import com.example.todolist2.Entity.Tache;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FormAjouterTache extends AppCompatActivity {
    // button qui permet de selectionner la date de début de la tache
    private Button btnSelectDateDebut;
    // button qui permet de selectionner la date de fin de la tache
    private Button btnSelectDateFin;
    // editText pour afficher la date debut choisi
    private EditText txtDateDebut;
    // editText pour afficher la date fin choisi
    private EditText txtDateFin;
    private SimpleDateFormat simpleDateFormat;
    private long dateDebut;
    private long dateFin;
    // EditText pour ecrire titre de la tache
    private EditText txtTitreTache;
    // EditText pour ecrire description de la tache
    private EditText txtDescriptionTache;
    private Button btnAjouterTache;
    private MyDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_form_ajouter_tache);
        btnSelectDateDebut = (Button) findViewById(R.id.btnSelectDateDebut);
        btnSelectDateFin = (Button) findViewById(R.id.btnSelectDateFin);
        txtDateDebut = (EditText) findViewById(R.id.txtDateDebut);
        txtDateFin = (EditText) findViewById(R.id.txtDateFin);
        simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        txtTitreTache = (EditText) findViewById(R.id.txtTitreTache);
        txtDescriptionTache = (EditText) findViewById(R.id.txtTitreTache);
        btnAjouterTache = (Button) findViewById(R.id.btnAjouterTache);
        // crée une instance de la base de données
        database = MyDatabase.getInstance(FormAjouterTache.this);
        // vérifier s'il y a des paramétres extra
        Bundle extra = this.getIntent().getExtras();
        if(extra == null) {
            btnAjouterTache.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String titreTache = txtTitreTache.getText().toString();
                    String descriptionTache = txtDescriptionTache.getText().toString();
                    Tache tache = new Tache(titreTache, descriptionTache, dateDebut, dateFin);
                    new AjouterTache(database).execute(tache);
                    // on configure l'alarme pour la déclencher au début de la tâche
                    setAlarm(tache.getDateDebut(), tache.getTitreTache());
                    createNotificationChannel(tache.getTitreTache(),tache.getDescriptionTache());
                    finish();
                    Toast.makeText(FormAjouterTache.this, "Nouvelle tâche est ajoutée", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            txtTitreTache.setText(extra.getString("titreTache"));
            txtDescriptionTache.setText(extra.getString("descriptionTache"));
            txtDateDebut.setText(extra.getString("dateDebut"));
            txtDateFin.setText(extra.getString("dateFin"));
            btnAjouterTache.setText("Modifier");
            Long idTache = extra.getLong("idTache");
            btnAjouterTache.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LiveData<Tache> tache = database.getTacheDao().recupererTacheParId(idTache);
                    tache.observe(FormAjouterTache.this, new Observer<Tache>() {
                        @Override
                        public void onChanged(Tache tache) {
                            tache.setTitreTache(txtTitreTache.getText().toString());
                            tache.setDescriptionTache(txtDescriptionTache.getText().toString());
                            new ModifierTache(database).execute(tache);
                        }
                    });
                }
            });
        }
    }
    public void afficherDateTimePickerDialog(View view) {
        if (view == btnSelectDateDebut){
            Calendar calendar = Calendar.getInstance();
            /*int annee = calendar.get(Calendar.YEAR);
            int mois = calendar.get(Calendar.MONTH);
            int jour = calendar.get(Calendar.DAY_OF_MONTH);*/
            int heure = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(FormAjouterTache.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int selectedHourOfDay, int selectedMinute) {
                    Calendar selectedDateTime = Calendar.getInstance();
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, selectedHourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, selectedMinute);
                    long dateDebutMillis = selectedDateTime.getTimeInMillis();
                    String formattedDateTime = simpleDateFormat.format(selectedDateTime.getTime());
                    txtDateDebut.setText(formattedDateTime);
                    setDateDebut(dateDebutMillis);
                }
                }, heure, minute, true);
                    timePickerDialog.show();
        }
        else if (view == btnSelectDateFin){
            Calendar calendar = Calendar.getInstance();
            int heure = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(FormAjouterTache.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int selectedHourOfDay, int selectedMinute) {
                    Calendar selectedDateTime = Calendar.getInstance();
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, selectedHourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, selectedMinute);
                    long dateFinMillis = selectedDateTime.getTimeInMillis();
                    String formattedDateTime = simpleDateFormat.format(selectedDateTime.getTime());
                    txtDateFin.setText(formattedDateTime);
                    setDateFin(dateFinMillis);
                    System.out.println("--- ==== ---- ******: "+dateFinMillis);
                }
                }, heure, minute, true);
                    timePickerDialog.show();
        }
    }
    public void setDateDebut(long dateDebut) {
        this.dateDebut = dateDebut;
    }
    public void setDateFin(long dateFin) {
        this.dateFin = dateFin;
    }
    private void setAlarm(long dateDebutTache, String titreTache) {
        AlarmManager alarmManager =
                (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, Notification.class);
        intent.putExtra("titreTache", titreTache);
        PendingIntent pIntent=PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        System.out.println("--- set Alarm ---");
        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                dateDebutTache,
                pIntent);
    }
    private void createNotificationChannel(String titreTache, String descriptionTache) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channelId", titreTache, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(descriptionTache);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}