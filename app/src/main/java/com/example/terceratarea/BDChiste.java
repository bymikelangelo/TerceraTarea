package com.example.terceratarea;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Chiste.class}, version = 5)
public abstract class BDChiste extends RoomDatabase {
    public abstract DaoChiste obtenerDao();

    public static BDChiste obtenerBD (Context contexto) {
        BDChiste bd = Room.databaseBuilder(contexto, BDChiste.class, "BDChiste")
                .allowMainThreadQueries().build();
        return bd;
    }
}
