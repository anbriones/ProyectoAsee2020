package com.example.fithealth.datos.model;

import androidx.room.TypeConverter;

public class tipoConverter {
    @TypeConverter
    public static Comida.Tipo toTipo(String tipo){
        return Comida.Tipo.valueOf(tipo);
    }

    @TypeConverter
    public static String toString(Comida.Tipo tipo){
        return tipo.name();
    }
}
