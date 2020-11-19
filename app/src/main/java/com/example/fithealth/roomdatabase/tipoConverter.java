package com.example.fithealth.roomdatabase;

import androidx.room.TypeConverter;

import com.example.fithealth.roomdatabase.Comida;

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
