package com.example.fithealth.ui.lecturaAPI;


import com.example.fithealth.lecturaJson.AlimentosFinales;



import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class AlimentosNetworkLoaderRunnable implements Runnable{
    private final OnFoodLoadedListener mOnFoodLoadedListener;

    public AlimentosNetworkLoaderRunnable(OnFoodLoadedListener onFoodLoadedListener){
        mOnFoodLoadedListener = onFoodLoadedListener;
    }

    @Override
    public void run()  {
        //new retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://drive.google.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlimentosService service = retrofit.create(AlimentosService.class);

        try {
            List<AlimentosFinales> aliments = service.getfoods().execute().body();
            //Llamada sincrona para poder hacer varias llamadas y usar datos de la llamada anterior
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                    mOnFoodLoadedListener.onFoodLoaded(aliments);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


