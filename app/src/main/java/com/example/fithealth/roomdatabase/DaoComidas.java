package com.example.fithealth.roomdatabase;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoComidas {
       @Insert
    public long addalimento(Alimento alim);//Inserción de datos
    @Insert
    public long  addcomida(Comida comida);//Inserción de datos

    @Insert
    public long  addalimcomida(AlimentoEnComida alimencomida);//Inserción de datos


   // Consulta que devuelve todos los alimentos ingeridos en una comida especifica entre dos fechas dadas

    @Query("SELECT * FROM Alimento "+
            "INNER JOIN AlimentoEnComida  ON AlimentoEnComida.idalimento = Alimento.id "+
            "INNER JOIN Comida ON Comida.id = AlimentoEnComida.idcomida " +
            " WHERE Comida.tipo LIKE :tipo and date between  :from AND :to ")
    public List <Alimento> getAlldiarias(String tipo,Long from,Long to);


    //Devuelve la suma de las calorias de los alimentos consumidos entre dos fechas indicadas

    @Query("SELECT SUM(calorias) from alimento " +
            "INNER JOIN AlimentoEnComida  ON AlimentoEnComida.idalimento = Alimento.id "+
            "INNER JOIN Comida ON Comida.id = AlimentoEnComida.idcomida " +
            " WHERE Comida.tipo LIKE :tipo and date between  :from AND :to ")
   public Integer getcaloriastotales(String tipo,Long from,Long to);

    @Query("SELECT SUM(calorias) from alimento " +
            "INNER JOIN AlimentoEnComida  ON AlimentoEnComida.idalimento = Alimento.id "+
            "INNER JOIN Comida ON Comida.id = AlimentoEnComida.idcomida " +
            " WHERE  date between  :from AND :to ")
    public Integer getAllcaloriasfecha(Long from,Long to);


    @Query("SELECT * FROM Alimento "+
            "INNER JOIN AlimentoEnComida  ON AlimentoEnComida.idalimento = Alimento.id "+
            "INNER JOIN Comida ON Comida.id = AlimentoEnComida.idcomida " +
            " WHERE date between  :from AND :to ")
    public List <Alimento> getAllfecha(Long from,Long to);

   // Obtener el id de las comidas donde aparece el alimento

    @Query("SELECT idcomida from alimentoencomida where idalimento like :idalim")
    public long obteneridcomida(Long idalim);

    @Delete
    public void deletealimento(Alimento alim);

    @Delete
    public void deletecomida(Comida comida);

    @Delete
    public void deletealimentoencomida(AlimentoEnComida alim);

    @Query("SELECT * FROM Comida "+
            " WHERE  id like:idcomi")
    public List <Comida> obtenercomidas(Long idcomi);

    @Query("SELECT * FROM Alimento "+
            " WHERE  id like:idalim")
    public Alimento obteneralimento(Long idalim);

    //Devolver una lista con las tuplas que tengan el id de alimentoy de comida pasados por parámetro
    @Query("SELECT * FROM ALIMENTOENCOMIDA "+
     " WHERE idalimento like :idalim and idcomida like:idcomi")
    public List <AlimentoEnComida> obteneralimentos(Long idalim,Long idcomi);

    //Update no hay puesto que una vez se han añadido los alimentos no se pueden modificar.

}
