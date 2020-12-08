package com.example.fithealth.datos.lecturaapi;

import com.example.fithealth.datos.model.AlimentosFinales;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface AlimentosService {

    @GET("uc?export=download&id=1sVvFMBSnCjLPIhuHn1QHfDSv73-qcbbM")
       Call<List<AlimentosFinales>> getfoods();


}
