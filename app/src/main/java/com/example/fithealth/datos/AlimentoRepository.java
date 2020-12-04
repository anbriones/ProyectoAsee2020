package com.example.fithealth.datos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fithealth.AppExecutors;
import com.example.fithealth.datos.lecturaapi.AlimentosNetworkDataSource;
import com.example.fithealth.datos.model.AlimentosFinales;
import com.example.fithealth.datos.roomdatabase.DaoAlimentojson;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlimentoRepository {
    private static final String LOG_TAG = AlimentoRepository.class.getSimpleName();

    // For Singleton instantiation
    private static AlimentoRepository sInstance;
    private final DaoAlimentojson mAlimjsonDao;
    private final AlimentosNetworkDataSource mAlimNetworkDataSource;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private final MutableLiveData<String> userFilterLiveData = new MutableLiveData<>();
    private final Map<String, Long> lastUpdateTimeMillisMap = new HashMap<>();
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private AlimentoRepository( DaoAlimentojson mAlimjsonDao,AlimentosNetworkDataSource mAlimNetworkDataSource) {
        this.mAlimjsonDao = mAlimjsonDao;
        this.mAlimNetworkDataSource = mAlimNetworkDataSource;
        // LiveData that fetches repos from network
        LiveData<AlimentosFinales[]> networkData = mAlimNetworkDataSource.getCurrentAlimentos();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.

        networkData.observeForever(newAlimentosFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deleting cached repos of user
                if (newAlimentosFromNetwork.length > 0){
                    mAlimjsonDao.eliminaralimentos();
                }

                // Insert our new repos into local database
                mAlimjsonDao.insertarAlimento(Arrays.asList(newAlimentosFromNetwork));
                Log.d(LOG_TAG, "New values inserted in Room");
            });
        });
    }

    public synchronized static AlimentoRepository getInstance(AlimentosNetworkDataSource nds, DaoAlimentojson dao) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new AlimentoRepository( dao,nds);
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }




    public void doFetchAlimentos() {
        Log.d(LOG_TAG, "Fetching Alimentos from Json");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mAlimjsonDao.eliminaralimentos();
            mAlimNetworkDataSource.fetchAlimentos();
            lastUpdateTimeMillisMap.put("",System.currentTimeMillis());
        });
    }



    public LiveData<List<AlimentosFinales>> getcurrentalimentos() {
        return  mAlimjsonDao.getalimentosfinales();
    }

    private boolean isFetchNeeded() {
        Long lastFetchTimeMillis = lastUpdateTimeMillisMap.get("");
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || mAlimjsonDao.getNumberAlimentos() == 0;
    }
}



