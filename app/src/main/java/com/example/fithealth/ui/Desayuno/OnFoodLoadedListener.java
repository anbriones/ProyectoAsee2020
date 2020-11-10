package com.example.fithealth.ui.Desayuno;

import com.example.fithealth.model.Alimentos;
import com.example.fithealth.model.Food;

import java.util.List;

public interface OnFoodLoadedListener {
    public void onReposLoaded(List <Food> alimentos);
}
