package com.example.fithealth.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;



    @Database(entities = {Alimento.class, Comida.class, AlimentoEnComida.class}, version=1)
public abstract class Comidasdatabase extends RoomDatabase {
    private static Comidasdatabase instance;

    public static Comidasdatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context, Comidasdatabase.class, "DATABASECOMIDAS.db").build();
        }
        return instance;
    }
    public  abstract DaoComidas daoAlim();

}
