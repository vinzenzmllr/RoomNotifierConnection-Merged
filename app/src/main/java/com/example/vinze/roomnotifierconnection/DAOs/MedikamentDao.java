package com.example.vinze.roomnotifierconnection.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vinze.roomnotifierconnection.Entities.Medikament;

import java.util.List;

@Dao
public interface MedikamentDao {

    @Insert
    void insert(Medikament medikament);

    @Update
    void update(Medikament medikament);

    @Delete
    void delete(Medikament medikament);

    @Query("DELETE FROM medikament_table")
    void deleteAllMedikamente();

    @Query("select * from medikament_table")
    LiveData<List<Medikament>> getAllMedikamente();
}
