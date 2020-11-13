package com.example.fithealth.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import androidx.room.Entity;


@Entity(tableName = "alimento")
public class Alimento {
    @Ignore
    public final static String ID = "id";
    @Ignore
    public final static String NOMBRE = "nombre";
    @Ignore
    public final static String CALORIAS = "calorias";
    @Ignore
    public final static String GRAMOS ="gramos";

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name="nombre")
    private String nombre;
    @ColumnInfo(name="calorias")
    private Integer calorias;
    @ColumnInfo(name="gramos")
    private Integer gramos;
    @ColumnInfo(name="unidad")
    private String unidad;

    public Alimento(String nombre, Integer calorias,Integer gramos, String unidad){
        this.nombre=nombre;
        this.gramos=gramos;
        this.calorias=calorias;
        this.unidad=unidad;
    }
    public Integer getGramos() {
        return gramos;
    }

    public void setGramos(Integer gramos) {
        this.gramos = gramos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String nombre) {
        this.unidad = unidad;
    }
}

