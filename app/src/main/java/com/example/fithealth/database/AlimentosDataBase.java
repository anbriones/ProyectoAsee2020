package com.example.fithealth.database;



import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Alimento.class}, version=1)
public abstract class AlimentosDataBase extends RoomDatabase {
private static AlimentosDataBase instance;

public static AlimentosDataBase getInstance(Context context){
    if(instance == null) {
        instance = Room.databaseBuilder(context, AlimentosDataBase.class, "database.db").build();
    }

    return instance;
}
    public  abstract  DaoAlimentos daoAlim();
   // public abstract DaoComidas daoCom();
}
