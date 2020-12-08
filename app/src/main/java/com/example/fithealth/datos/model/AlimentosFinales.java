
package com.example.fithealth.datos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName="alimentojson")
public class AlimentosFinales implements Serializable {


    @SerializedName("nombreprod")
    @Expose
    @PrimaryKey
    @NonNull
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
    @SerializedName("hidratos")
    @Expose
    @ColumnInfo(name = "hidratos")
    private Double hidratos;
    @SerializedName("urlimagen")
    @Expose
    @ColumnInfo(name = "urlimagen")
    private String urlimagen;
    @SerializedName("tipo")
    @Expose
    @ColumnInfo(name = "tipo")
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
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

    public Double getHidratos() {
        return hidratos;
    }

    public void setHidratos(Double hidratos) {
        this.hidratos = hidratos;
    }
}
