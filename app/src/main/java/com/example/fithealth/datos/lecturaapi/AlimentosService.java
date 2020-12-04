package com.example.fithealth.datos.lecturaapi;

import com.example.fithealth.datos.model.AlimentosFinales;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface AlimentosService {

    @GET("uc?export=download&id=1Rm0bD36EfCBVBbLjhhI80IVx1eRAtRi0")
       Call<List<AlimentosFinales>> getfoods();


}
