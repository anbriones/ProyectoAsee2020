package com.example.fithealth.ui.lecturaAPI;

import com.example.fithealth.lecturaJson.AlimentosFinales;
import com.example.fithealth.model.Food;

import java.util.List;

public interface OnFoodLoadedListener {
    public void onFoodLoaded(List <AlimentosFinales> alimentos);
}
