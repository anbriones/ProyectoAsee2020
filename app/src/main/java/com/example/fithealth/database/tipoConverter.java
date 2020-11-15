package com.example.fithealth.database;

import androidx.room.TypeConverter;

import com.example.fithealth.database.Comidas;

public class tipoConverter {
    @TypeConverter
    public static Alimento.Tipo toTipo(String tipo){
        return Alimento.Tipo.valueOf(tipo);
    }

    @TypeConverter
    public static String toString(Alimento.Tipo tipo){
        return tipo.name();
    }
}
