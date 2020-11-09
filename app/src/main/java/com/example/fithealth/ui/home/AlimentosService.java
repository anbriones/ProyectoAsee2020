package com.example.fithealth.ui.home;

import com.example.fithealth.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlimentosService {
    @GET("/foods")
    Call<List<Food>> getAlimentos();
}
