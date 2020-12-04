package com.example.fithealth.datos.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fithealth.datos.model.AlimentoEnComida;

import java.util.List;
@Dao
public interface DaoAlimentosEnComida {
    @Insert
    public long  addalimcomida(AlimentoEnComida alimencomida);//Inserción de datos

    //Eliminar el alimento en comida que se ha especificado
    @Delete
    public void deletealimentoencomida(AlimentoEnComida alim);


    //Devolver una lista con las tuplas que tengan el id de alimentoy de comida pasados por parámetro
    @Query("SELECT * FROM ALIMENTOENCOMIDA "+
            " WHERE idalimento like :idalim and idcomida like:idcomi")
    public List<AlimentoEnComida> obteneralimentos(Long idalim, Long idcomi);
}
