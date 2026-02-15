package com.braguia.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Trail.class, User.class, Pin.class, Edge.class}, version = 985)
public abstract class BraguiaDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "BraguiaDatabase";

    public abstract TrailDAO trailDAO();
    public abstract UserDAO userDAO();
    public abstract PinDAO pinDAO();
    public abstract EdgeDAO edgeDAO();

    public static volatile BraguiaDatabase INSTANCE = null;

    public static BraguiaDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BraguiaDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, BraguiaDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsyn(INSTANCE);
        }
    };

    static class PopulateDbAsyn extends AsyncTask<Void,Void,Void> {

        private TrailDAO traildao;

        private UserDAO userdao;

        private PinDAO pindao;

        private EdgeDAO edgedao;

        public PopulateDbAsyn(BraguiaDatabase catDatabase) {
            traildao = catDatabase.trailDAO();
            userdao = catDatabase.userDAO();
            pindao = catDatabase.pinDAO();
            edgedao = catDatabase.edgeDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            traildao.deleteAll();
            userdao.deleteAll();
            pindao.deleteAll();
            edgedao.deleteAll();

            return null;
        }
    }
}