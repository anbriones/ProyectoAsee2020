
package com.example.fithealth.lecturaJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlimentosFinales {

    @SerializedName("nombreprod")
    @Expose
    private String nombreprod;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("unidad")
    @Expose
    private String unidad;
    @SerializedName("cantidad")
    @Expose
    private Integer cantidad;
    @SerializedName("calorias")
    @Expose
    private Integer calorias;
    @SerializedName("proteinas")
    @Expose
    private Double proteinas;
    @SerializedName("Hidratos de Carbono")
    @Expose
    private Integer hidratosDeCarbono;

    public String getNombreprod() {
        return nombreprod;
    }

    public void setNombreprod(String nombreprod) {
        this.nombreprod = nombreprod;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
