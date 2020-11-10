package com.example.fithealth.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;
import android.content.Context;




@Database(entities = {Alimento.class, Comidas.class}, version=1)
public abstract class DataBase extends RoomDatabase {

    public  abstract  DaoAlimentos daoAlim();
    public abstract DaoComidas daoCom();


}
