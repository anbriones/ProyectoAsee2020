package com.example.fithealth.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface DaoAlimentos {

/*Consulta que devuelve todos los alimentos consumidos en una comida, las fechas son para que a
 las 12 de la noche se limpie la pantalla y así sean visibles solo los alimentos consumidos ese día.
 */
    @Query("SELECT * from alimento where Tipo like :tipo and date between  :from AND :to ")
    public List <Alimento> getAlldiarias(String tipo,Long from,Long to); //Devuelve todos los alimentos de la base de datos que sean del tipo indicado
/*
Consulta que devuelve la cantidad de calorias que se han ingerido en cada comida
 */
    @Query("SELECT SUM(calorias) from alimento where Tipo like :tipo and date between  :from AND :to ")
    public Integer getcaloriastotales(String tipo,Long from,Long to); //Devuelve todos los alimentos de la base de datos que sean del tipo indicado
/*
Consulta que devuelve todos los alimentos consumidos entre dos fechas dadas,
es para la parte del historial, mostrar por cada día los alimentos consumidos
 */
    @Query("SELECT * from alimento WHERE date between  :from AND :to ")
    public List <Alimento> getAllfecha(Long from,Long to); //Devuelve todos los alimentos de la base de datos que sean de una fecha indicada
/*
Devuelve el número de calorias ingeridas entre dos fechas dadas, también es para el historial.
 */
    @Query("SELECT SUM(calorias) from alimento WHERE date between  :from AND :to ")
    public Integer  getAllcaloriasfecha(Long from,Long to); //Devuelve todos los alimentos de la base de datos que sean de una fecha indicada
/*
Elimina el alimento que se quiere
 */
    @Delete
    public void deletealimento(Alimento alim);
/*
Inserción del alimento creado
 */
    @Insert
    public void addalimento(Alimento alim);//Inserción de datos
    //buscar un alimento en la base de lista


    //Update no hay puesto que una vez se han añadido los alimentos no se pueden modificar.

}
