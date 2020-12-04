package com.example.fithealth.datos.lecturaapi;


import com.example.fithealth.datos.model.AlimentosFinales;

import java.util.List;

public interface OnFoodLoadedListener {
    public void onFoodLoaded(List <AlimentosFinales> alimentos);
}
