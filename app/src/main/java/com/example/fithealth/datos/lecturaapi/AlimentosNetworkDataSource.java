package com.example.fithealth.datos.lecturaapi;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fithealth.AppExecutors;
import com.example.fithealth.datos.model.AlimentosFinales;


public class AlimentosNetworkDataSource {
    private static final String LOG_TAG =AlimentosNetworkDataSource.class.getSimpleName();
    private static AlimentosNetworkDataSource sInstance;
    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<AlimentosFinales[]> mDownloadedAlimentosfinales;

    private  AlimentosNetworkDataSource() {
        mDownloadedAlimentosfinales = new MutableLiveData<>();
    }

    public synchronized static AlimentosNetworkDataSource getInstance() {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            sInstance = new AlimentosNetworkDataSource();
            Log.d(LOG_TAG, "Made new network data source");
        }
        return sInstance;
    }


    public LiveData<AlimentosFinales[]> getCurrentAlimentosfinales() {
        return mDownloadedAlimentosfinales;
    }

    public void fetchAlimentos() {
        Log.d(LOG_TAG, "Fetch alimentos started");
        // Get data from network and pass it to LiveData
        AppExecutors.getInstance().networkIO().execute(new AlimentosNetworkLoaderRunnable( alimentosFinales ->
                mDownloadedAlimentosfinales.postValue(alimentosFinales.toArray(new AlimentosFinales[0]))));


    }

}






