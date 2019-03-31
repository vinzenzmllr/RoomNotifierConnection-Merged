package com.example.vinze.roomnotifierconnection.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vinze.roomnotifierconnection.Entities.Reminder;

import java.util.List;

@Dao
public interface ReminderDAO {

    @Insert
    long insert(Reminder reminder);

    @Update
    void update(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

    @Query("DELETE FROM r_reminder")
    void deleteAllReminders();

    @Query("SELECT * FROM r_reminder")
    LiveData<List<Reminder>> getAllReminders();

}
