package com.example.fithealth.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoAlimentos {

    @Query("SELECT * from alimento")
    public List <Alimento> getAll(); //Devuelve todos los alimentos de la base de datos

    @Insert
    public void addalimento(Alimento alim);//Inserción de datos
    //buscar un alimento en la base de lista

    @Query("SELECT * FROM alimento WHERE  nombre LIKE :busqueda" )
    public List<Alimento> buscaralim(String busqueda);

    @Query("DELETE from alimento")
    public void eliminarAlimento();
    //Update no va a haber puesto que sólo se van a poder escoger los alimentos de la base de datos y tienen datos fijos

}
