package com.example.todolist2.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolist2.Entity.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    Long ajouterNote(Note note);
    @Update
    int modifierNote(Note note);
    @Query("SELECT * FROM note")
    LiveData<List<Note>> recupererNotes();
    @Delete
    void supprimerNote(Note note);
}
