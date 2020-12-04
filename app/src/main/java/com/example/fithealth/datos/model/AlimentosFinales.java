
package com.example.fithealth.datos.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName="alimentojson")
public class AlimentosFinales {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("nombreprod")
    @Expose
    @ColumnInfo(name = "nombreprod")
    private String nombreprod;
    @SerializedName("unidad")
    @Expose
    @ColumnInfo(name = "unidad")
    private String unidad;
    @SerializedName("cantidad")
    @Expose
    @ColumnInfo(name = "cantidad")
    private Integer cantidad;
    @SerializedName("calorias")
    @Expose
    @ColumnInfo(name = "calorias")
    private Integer calorias;
    @SerializedName("proteinas")
    @Expose
    @ColumnInfo(name = "proteinas")
    private Double proteinas;
    @SerializedName("hidratosdecarbono")
    @Expose
    @ColumnInfo(name = "hidratosdecarbono")
    private Integer hidratosDeCarbono;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreprod() {
        return nombreprod;
    }

    public void setNombreprod(String nombreprod) {
        this.nombreprod = nombreprod;
    }

   public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    public Double getProteinas() {
        return proteinas;
    }

    public void setProteinas(Double proteinas) {
        this.proteinas = proteinas;
    }

    public Integer getHidratosDeCarbono() {
        return hidratosDeCarbono;
    }

    public void setHidratosDeCarbono(Integer hidratosDeCarbono) {
        this.hidratosDeCarbono = hidratosDeCarbono;
    }

}
