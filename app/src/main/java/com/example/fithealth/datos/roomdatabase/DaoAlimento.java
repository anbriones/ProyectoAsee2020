package com.example.fithealth.datos.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fithealth.datos.model.Alimento;

import java.util.List;
@Dao
public interface DaoAlimento {
    @Insert
    public long addalimento(Alimento alim);//Inserción de datos


    // Consulta que devuelve todos los alimentos ingeridos en una comida especifica entre dos fechas dadas
    @Query("SELECT * FROM Alimento "+
            "INNER JOIN AlimentoEnComida  ON AlimentoEnComida.idalimento = Alimento.id "+
            "INNER JOIN Comida ON Comida.id = AlimentoEnComida.idcomida " +
            " WHERE Comida.tipo LIKE :tipo and date between  :from AND :to ")
    public LiveData<List<Alimento>> getAlldiariascomidas(String tipo, Long from, Long to);

    //Devuelve la suma de las calorias de los alimentos consumidos entre dos fechas indicadas en una comida específica(Desayuno,comida,cena)
    @Query("SELECT SUM(calorias) from alimento " +
            "INNER JOIN AlimentoEnComida  ON AlimentoEnComida.idalimento = Alimento.id "+
            "INNER JOIN Comida ON Comida.id = AlimentoEnComida.idcomida " +
            " WHERE Comida.tipo LIKE :tipo and date between  :from AND :to ")
    public LiveData<Integer> getcaloriastotalescomidas(String tipo,Long from,Long to);

    //Devuelve el total de calorias que se han consumido en una fecha
    @Query("SELECT SUM(calorias) from alimento " +
            "INNER JOIN AlimentoEnComida  ON AlimentoEnComida.idalimento = Alimento.id "+
            "INNER JOIN Comida ON Comida.id = AlimentoEnComida.idcomida " +
            " WHERE  date between  :from AND :to ")
    public LiveData<Integer >getAllcaloriasfecha(Long from,Long to);

    //Devuelve una lista de todos los alimentos consumidos en una día específico
    @Query("SELECT * FROM Alimento "+
            "INNER JOIN AlimentoEnComida  ON AlimentoEnComida.idalimento = Alimento.id "+
            "INNER JOIN Comida ON Comida.id = AlimentoEnComida.idcomida " +
            " WHERE  date  between :from AND :to ")
    LiveData<List<Alimento>> getAllfechalivedata(Long from,Long to);

    @Query("SELECT * FROM Alimento "+
            " WHERE  id like:idalim")
    public Alimento obteneralimento(Long idalim);


    //Eliminar alimento
    @Delete
    public void deletealimento(Alimento alim);

    //Eliminar alimento

    @Query
    ("SELECT * FROM Alimento ")
    public List<Alimento>  totalalimentos();
}
