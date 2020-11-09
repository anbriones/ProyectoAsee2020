
package com.example.fithealth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("revisionId")
    @Expose
    private String revisionId;
    @SerializedName("brand")
    @Expose
    private Brand brand;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("classification")
    @Expose
    private String classification;
    @SerializedName("mass")
    @Expose
    private Double mass;

    public String getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(String revisionId) {
        this.revisionId = revisionId;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

}
