package com.example.fithealth.datos.roomdatabase;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fithealth.datos.model.Comida;

import java.util.List;

@Dao
public interface DaoComidas {
    @Insert
    public long  addcomida(Comida comida);//Inserción de datos

   // Obtener el id de las comidas donde aparece el alimento
    @Query("SELECT idcomida from alimentoencomida where idalimento like :idalim")
    public long obteneridcomida(Long idalim);

    //Eliminar comida
    @Delete
    public void deletecomida(Comida comida);

    @Query("SELECT * FROM Comida "+
            " WHERE  id like:idcomi")
    public List <Comida> obtenercomidas(Long idcomi);


    //Update no hay puesto que una vez se han añadido los alimentos no se pueden modificar.

}
