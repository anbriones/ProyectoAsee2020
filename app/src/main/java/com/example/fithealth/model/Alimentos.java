
package com.example.fithealth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alimentos {

    @SerializedName("nombreprod")
    @Expose
    private String nombreProd;
    @SerializedName("unidad")
    @Expose
    private String unidad;
    @SerializedName("cantidad")
    @Expose
    private Integer cantidad;
    @SerializedName("calorias")
    @Expose
    private Integer calorias;

    public String getNombreProd() {
        return nombreProd;
    }

    public void setNombreProd(String nombreProd) {
        this.nombreProd = nombreProd;
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

}
