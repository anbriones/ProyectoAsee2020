package com.example.fithealth.ui.lecturaAPI;

import com.example.fithealth.model.Food;

import java.util.List;

public interface OnFoodLoadedListener {
    public void onReposLoaded(List <Food> alimentos);
}