package com.example.fithealth.datos.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Entity(tableName = "comida",indices = {@Index(value =
        {"date"}, unique = true)})
public class Comida {
    @Ignore
    public final static String ID = "id";
    @Ignore
    public final static String TIPO = "tipo";
    @Ignore
    public final static String FECHA ="fecha"; //fecha en la que se realiza la comida
    @Ignore
    public final static SimpleDateFormat FORMAT= new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public enum Tipo{desayuno,comida,cena};

    @PrimaryKey(autoGenerate = true)
    private long id;
    @TypeConverters(tipoConverter.class)
    private Tipo tipo= Tipo.desayuno; //Desayuno comida  cena o que se la cree el usuario
       @TypeConverters(DateConverter.class)
    private Date date=new Date();

    public Comida(Tipo tipo, Date date) {
        this.tipo = tipo;
        this.date = date;
    }

    @Ignore
    public Comida(Integer id, Tipo tipo, Date date) {
        this.id = id;
        this.tipo = tipo;
        this.date = date;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

}
