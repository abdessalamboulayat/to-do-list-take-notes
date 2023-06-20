package com.example.todolist2.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @ColumnInfo(name = "id_note")
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "titre")
    private String titre;
    @ColumnInfo(name = "description")
    private String description;
    public Note(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
