package com.example.fithealth.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fithealth.roomdatabase.Alimento;
import com.example.fithealth.roomdatabase.AlimentoEnComida;
import com.example.fithealth.roomdatabase.Comida;

@Database(entities = {Alimento.class, Comida.class, AlimentoEnComida.class}, version=1)
public abstract class Comidasdatabase extends RoomDatabase {
    private static com.example.fithealth.roomdatabase.Comidasdatabase instance;

    public static com.example.fithealth.roomdatabase.Comidasdatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context, com.example.fithealth.roomdatabase.Comidasdatabase.class, "DATABASECOMIDAS.db").build();
        }

        return instance;
    }
    public  abstract DaoComidas daoAlim();

}
