package com.example.fithealth.ui.home;


import com.example.fithealth.Food;

import java.io.IOException;
import java.util.List;

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
            List<Food> aliments = service.getAlimentos().execute().body();
            //Llamada sincrona para poder hacer varias llamadas y usar datos de la llamada anterior
            AppExecutors.getInstance().mainThread().execute(() -> mOnFoodLoadedListener.onReposLoaded(aliments));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


