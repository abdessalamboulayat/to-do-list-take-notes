package com.example.todolist2.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Tache {
    @ColumnInfo(name = "id_tache")
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "titre_tache")
    private String titreTache;
    @ColumnInfo(name = "description_tache")
    private String descriptionTache;
    @ColumnInfo(name = "date_debut")
    private long dateDebut;
    @ColumnInfo(name = "date_fin")
    private long dateFin;
    @ColumnInfo(name = "termine")
    private boolean termine;

    public Tache(String titreTache, String descriptionTache, long dateDebut, long dateFin) {
        this.titreTache = titreTache;
        this.descriptionTache = descriptionTache;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitreTache() {
        return titreTache;
    }

    public void setTitreTache(String titreTache) {
        this.titreTache = titreTache;
    }

    public String getDescriptionTache() {
        return descriptionTache;
    }

    public void setDescriptionTache(String descriptionTache) {
        this.descriptionTache = descriptionTache;
    }

    public long getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(long dateDebut) {
        this.dateDebut = dateDebut;
    }

    public long getDateFin() {
        return dateFin;
    }

    public void setDateFin(long dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isTermine() {
        return termine;
    }

    public void setTermine(boolean termine) {
        this.termine = termine;
    }
}
