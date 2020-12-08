package com.example.fithealth.datos.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fithealth.datos.model.Alimento;
import com.example.fithealth.datos.model.AlimentoEnComida;
import com.example.fithealth.datos.model.AlimentosFinales;
import com.example.fithealth.datos.model.Comida;


@Database(entities = {Alimento.class, Comida.class, AlimentoEnComida.class, AlimentosFinales.class}, version = 1, exportSchema = false)
public abstract class Comidasdatabase extends RoomDatabase {
    private static Comidasdatabase instance;

    public static Comidasdatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context, Comidasdatabase.class, "databasealimentos.db").build();
        }
        return instance;
    }
    public  abstract DaoAlimento daoAlim();
    public  abstract DaoComidas daoComidas();
    public  abstract DaoAlimentosEnComida daoAlimentosEnComida();
    public  abstract DaoAlimentojson daoAlimentojson();


}
