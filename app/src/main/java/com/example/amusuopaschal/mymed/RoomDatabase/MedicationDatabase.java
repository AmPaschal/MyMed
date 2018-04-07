package com.example.amusuopaschal.mymed.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Amusuo Paschal on 03/04/2018.
 */
@Database(entities = {Medication.class}, version = 1)
public abstract class MedicationDatabase extends RoomDatabase{
    public abstract MedicationDao medicationDao();

}
