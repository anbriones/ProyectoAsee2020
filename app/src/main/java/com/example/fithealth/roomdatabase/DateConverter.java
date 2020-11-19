package com.example.fithealth.roomdatabase;

import androidx.room.TypeConverter;


import java.util.Date;
public class DateConverter {

    @TypeConverter
    public static Long toTimestamp(Date d){
        return d == null ? null : d.getTime();
    }
    @TypeConverter
    public static Date toDate (Long t ){
        return t == null ? null: new Date(t);
    }

}
