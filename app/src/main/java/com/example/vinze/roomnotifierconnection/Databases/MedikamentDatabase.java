package com.example.vinze.roomnotifierconnection.Databases;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


import com.example.vinze.roomnotifierconnection.Activities.SearchActivity;
import com.example.vinze.roomnotifierconnection.DAOs.MedikamentDao;
import com.example.vinze.roomnotifierconnection.Entities.Medikament;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Medikament.class}, version = 1)
public abstract class MedikamentDatabase extends RoomDatabase {

    private static MedikamentDatabase instance;

    public abstract MedikamentDao medikamentDao();

    public static synchronized MedikamentDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MedikamentDatabase.class, "medikament_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MedikamentDao medikamentDao;

        private PopulateDbAsyncTask(MedikamentDatabase db) {
            medikamentDao = db.medikamentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<Medikament> medikamentList = new ArrayList<>();

            medikamentList = SearchActivity.getInstance().readCSV();

            for (Medikament medikament : medikamentList) {
                medikamentDao.insert(medikament);
            }

            return null;
        }
    }
}
