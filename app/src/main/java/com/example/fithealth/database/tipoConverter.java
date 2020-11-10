package com.example.fithealth.database;

import androidx.room.TypeConverter;

import com.example.fithealth.database.Comidas;

public class tipoConverter {
    @TypeConverter
    public static Comidas.Tipo toTipo(String tipo){
        return Comidas.Tipo.valueOf(tipo);
    }

    @TypeConverter
    public static String toString(Comidas.Tipo tipo){
        return tipo.name();
    }
}
