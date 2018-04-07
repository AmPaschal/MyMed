package com.example.amusuopaschal.mymed.RoomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import java.util.List;

/**
 * Created by Amusuo Paschal on 03/04/2018.
 */
@Dao
public interface MedicationDao {

    @Query("SELECT * FROM medications")
    List<Medication> getAllList();

    @Query("SELECT * FROM medications")
    Cursor getAllCursor();

    @Query("SELECT * FROM medications WHERE id = :id")
    Medication findById(int id);

    @Insert
    void insertAll(Medication... medications);

    @Delete
    void delete(Medication medication);

}
