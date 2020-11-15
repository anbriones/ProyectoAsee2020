package com.example.fithealth.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface DaoAlimentos {

    @Query("SELECT * from alimento where Tipo like :tipo")
    public List <Alimento> getAll(String tipo); //Devuelve todos los alimentos de la base de datos que sean del tipo indicado


    @Query("SELECT SUM(calorias) from alimento where Tipo like :tipo")
    public Integer getcaloriastotales(String tipo); //Devuelve todos los alimentos de la base de datos que sean del tipo indicado


    /*
    @Query("SELECT * from alimento where date like :fecha")
    public List <Alimento> getAll(Date fecha); //Devuelve todos los alimentos de la base de datos que sean de una fecha indicada
*/

    @Insert
    public void addalimento(Alimento alim);//Inserción de datos
    //buscar un alimento en la base de lista

    @Query("SELECT * FROM alimento WHERE  nombre LIKE :busqueda" )
    public List<Alimento> buscaralim(String busqueda);

    @Query("DELETE from alimento")
    public void eliminarAlimento();
    //Update no va a haber puesto que sólo se van a poder escoger los alimentos de la base de datos y tienen datos fijos

}
