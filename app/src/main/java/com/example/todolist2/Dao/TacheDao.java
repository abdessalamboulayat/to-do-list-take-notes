package com.example.todolist2.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist2.Entity.Tache;

import java.util.List;

@Dao
public interface TacheDao {
    @Insert
    Long ajouterTache(Tache tache);
    @Update
    int modifierTache(Tache tache);
    @Query("SELECT * FROM tache")
    LiveData<List<Tache>> recupererLesTaches();
    @Query("SELECT * FROM tache WHERE id_tache = :id")
    LiveData<Tache> recupererTacheParId(Long id);
    @Delete
    void supprimerTache(Tache tache);
}
