
package com.example.fithealth.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alimentos {

    @SerializedName("metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("foods")
    @Expose
    private List<Food> foods = null;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

}
