package com.example.fithealth.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


@Entity(tableName = "alimento")
public class Alimento {

    public enum Tipo{desayuno,comida,cena};

    @Ignore
    public final static String ID = "id";
    @Ignore
    public final static String NOMBRE = "nombre";
    @Ignore
    public final static String CALORIAS = "calorias";
    @Ignore
    public final static String GRAMOS ="gramos";
    @Ignore
    public final static String TIPO = "tipo";
    @Ignore
    public final static String FECHA ="fecha"; //fecha en la que se realiza la comida
    @Ignore
    public final static SimpleDateFormat FORMAT= new SimpleDateFormat("yyyy-MM-dd", Locale.US);



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
    @TypeConverters(tipoConverter.class)
    private Tipo tipo=Tipo.desayuno; //Desayuno comida  cena o que se la cree el usuario
    @TypeConverters(DateConverter.class)
    private Date date=new Date();

    public Alimento(String nombre, Integer calorias,Integer gramos, String unidad) {
        this.nombre = nombre;
        this.gramos = gramos;
        this.calorias = calorias;
        this.unidad = unidad;

    }

    public Alimento(String nombre, Integer calories,Integer gramos, String unidad,Tipo tipo,Date fecha){
        this.nombre=nombre;
        this.gramos=gramos;
        this.calorias=calories;
        this.unidad=unidad;
        this.tipo=tipo;
        this.date=fecha;

    }
@Ignore
    public Alimento(String nombre, Integer calorias,Integer gramos, String unidad,Tipo tipo,String  fecha){
        this.nombre=nombre;
        this.gramos=gramos;
        this.calorias=calorias;
        this.unidad=unidad;
        this.tipo=tipo;
        try {
            this.date = Alimento.FORMAT.parse(fecha);
        } catch (ParseException e) {
            this.date = new Date();
        }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Tipo getTipo(){return tipo;}

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

}

