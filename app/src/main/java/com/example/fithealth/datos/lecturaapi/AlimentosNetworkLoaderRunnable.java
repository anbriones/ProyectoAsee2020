package com.example.fithealth.datos.lecturaapi;

import android.util.Log;

import com.example.fithealth.AppExecutors;
import com.example.fithealth.datos.model.AlimentosFinales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlimentosNetworkLoaderRunnable implements Runnable{
    private static final String LOG_TAG = AlimentosNetworkLoaderRunnable.class.getSimpleName();
    private final OnFoodLoadedListener mOnFoodLoadedListener;

    public AlimentosNetworkLoaderRunnable(OnFoodLoadedListener onFoodLoadedListener){
        mOnFoodLoadedListener = onFoodLoadedListener;
    }

    @Override
    public void run()  {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://drive.google.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       AlimentosService service = retrofit.create(AlimentosService.class);
        Call<List<AlimentosFinales>> call = service.getfoods();
        try {

            Response<List<AlimentosFinales>> response = call.execute();
            List<AlimentosFinales> alimentos = response.body() == null ? new ArrayList<>() : response.body();
            AppExecutors.getInstance().mainThread().execute(() -> Log.d(LOG_TAG, "Cargados"+alimentos.size()));
            AppExecutors.getInstance().mainThread().execute(() -> mOnFoodLoadedListener.onFoodLoaded(alimentos));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}


