package com.example.fithealth.datos.roomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fithealth.datos.model.AlimentosFinales;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
    public interface DaoAlimentojson {
        @Insert(onConflict = REPLACE)
        void insertarAlimento(List<AlimentosFinales> alimentosFinales);

        @Query("SELECT * FROM alimentojson")
        LiveData<List<AlimentosFinales>> getalimentosfinales();

        @Query("SELECT count(*) FROM alimentojson ")
        int getNumberAlimentos();


        @Query("SELECT * FROM alimentojson ")
        int eliminaralimentos();
    }


