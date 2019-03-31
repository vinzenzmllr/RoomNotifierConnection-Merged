package com.example.vinze.roomnotifierconnection.Entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "r_reminder")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "r_medicationName")
    private String medicationName;

    @ColumnInfo(name = "r_morningDate")
    private String morningTime;

    @ColumnInfo(name = "r_noonDate")
    private String noonTime;

    @ColumnInfo(name = "r_eveningDate")
    private String eveningTime;

    @ColumnInfo(name = "r_isOn")
    private Boolean isOn;

    public Reminder(String medicationName, String morningTime, String noonTime, String eveningTime, Boolean isOn) {
        this.medicationName = medicationName;
        this.morningTime = morningTime;
        this.noonTime = noonTime;
        this.eveningTime = eveningTime;
        this.isOn = isOn;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getMorningTime() {
        return morningTime;
    }

    public void setMorningTime(String morningTime) {
        this.morningTime = morningTime;
    }

    public String getNoonTime() {
        return noonTime;
    }

    public void setNoonTime(String noonTime) {
        this.noonTime = noonTime;
    }

    public String getEveningTime() {
        return eveningTime;
    }

    public void setEveningTime(String eveningTime) {
        this.eveningTime = eveningTime;
    }

    public Boolean getOn() {
        return isOn;
    }

    public void setOn(Boolean on) {
        isOn = on;
    }
}
