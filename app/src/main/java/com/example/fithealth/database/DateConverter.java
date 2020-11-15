package com.example.fithealth.database;

import androidx.room.TypeConverter;


import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(String timestamp){
    return null == timestamp ? new Date(timestamp) : null;
    }
    @TypeConverter
    public static Long toTimestamp(Date date){
        return date== null ? null : date.getTime();
    }
}
