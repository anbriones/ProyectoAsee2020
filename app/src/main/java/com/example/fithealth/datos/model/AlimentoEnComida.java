package com.example.fithealth.datos.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alimentoencomida")
public class AlimentoEnComida {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "idalimento")
    private long idalimento;
    @ColumnInfo(name = "idcomida")
    private long idcomida;


    public AlimentoEnComida(long idalimento, long idcomida) {
        this.idalimento = idalimento;
        this.idcomida = idcomida;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdalimento() {
        return idalimento;
    }

    public void setIdalimento(long idalimento) {
        this.idalimento = idalimento;
    }

    public long getIdcomida() {
        return idcomida;
    }

    public void setIdcomida(long idcomida) {
        this.idcomida = idcomida;
    }
}
