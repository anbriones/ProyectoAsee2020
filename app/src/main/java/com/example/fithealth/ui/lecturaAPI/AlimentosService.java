package com.example.fithealth.ui.lecturaAPI;


import com.example.fithealth.model.Alimentos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlimentosService {
    @GET("/foods")
    Call <Alimentos>  getAlimentos(@Query("query") String categoria, @Query("region") String region,
                                   @Query("fields") String fields);
}
