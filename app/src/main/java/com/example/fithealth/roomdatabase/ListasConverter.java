package com.example.fithealth.roomdatabase;

import androidx.room.TypeConverter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ListasConverter {


    static Gson gson = new Gson();

        @TypeConverter
        public static List<Alimento> stringToSomeObjectList(String data) {
            if (data == null) {
                return Collections.emptyList();
            }

            Type listType = new TypeToken<List<Alimento>>() {}.getType();
            return gson.fromJson(data, listType);
        }

        @TypeConverter
        public static String AlimentoListToString(List<Alimento> alimentos) {
            return gson.toJson(alimentos);
        }
    }

