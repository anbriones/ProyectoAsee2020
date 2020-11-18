package com.example.fithealth.ui.lecturaAPI;


import com.example.fithealth.model.Alimentos;


import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class AlimentosNetworkLoaderRunnable implements Runnable{
    private final OnFoodLoadedListener mOnFoodLoadedListener;

    public AlimentosNetworkLoaderRunnable(OnFoodLoadedListener onReposLoadedListener){
        mOnFoodLoadedListener = onReposLoadedListener;
    }

    @Override
    public void run()  {
        //new retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://foodapi.calorieking.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlimentosService service = retrofit.create(AlimentosService.class);

        try {
            Alimentos aliments = service.getAlimentos("burger","us","$summary,mass").execute().body();
            //Llamada sincrona para poder hacer varias llamadas y usar datos de la llamada anterior
            AppExecutors.getInstance().mainThread().execute(() -> mOnFoodLoadedListener.onReposLoaded(aliments.getFoods()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


