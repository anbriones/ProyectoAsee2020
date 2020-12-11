package com.example.fithealth.datos;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.fithealth.AppExecutors;
import com.example.fithealth.datos.lecturaapi.AlimentosNetworkDataSource;
import com.example.fithealth.datos.model.Alimento;
import com.example.fithealth.datos.model.AlimentoEnComida;
import com.example.fithealth.datos.model.AlimentosFinales;
import com.example.fithealth.datos.model.Comida;
import com.example.fithealth.datos.roomdatabase.DaoAlimento;
import com.example.fithealth.datos.roomdatabase.DaoAlimentojson;
import com.example.fithealth.datos.roomdatabase.DaoAlimentosEnComida;
import com.example.fithealth.datos.roomdatabase.DaoComidas;

import java.util.Arrays;
import java.util.List;

public class AlimentoRepository {
    private static final String LOG_TAG = AlimentoRepository.class.getSimpleName();

    // For Singleton instantiation
    private static AlimentoRepository sInstance;
    private final DaoAlimentojson mAlimjsonDao;
    private final DaoAlimento mAlimentos;
    private final DaoComidas mComidas;
    private final DaoAlimentosEnComida  mAlimentosEnComidas;
    private final AlimentosNetworkDataSource mAlimNetworkDataSource;

    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private final MutableLiveData<Long> userFilterLiveData1 = new MutableLiveData<>();
    private final MutableLiveData<Long> userFilterLiveData2 = new MutableLiveData<>();

    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private AlimentoRepository(DaoAlimentojson mAlimjsonDao, AlimentosNetworkDataSource mAlimNetworkDataSource, DaoAlimento daoAlim, DaoComidas daoComida, DaoAlimentosEnComida daoAlimentosEnComida) {
        this.mAlimjsonDao = mAlimjsonDao;
        this.mAlimNetworkDataSource = mAlimNetworkDataSource;
        this.mAlimentos = daoAlim;
        this.mComidas=daoComida;
        this.mAlimentosEnComidas= daoAlimentosEnComida;
        doFetchAlimentos();
        // LiveData that fetches alimentosjson from network
        LiveData<AlimentosFinales[]> networkData = this.mAlimNetworkDataSource.getCurrentAlimentosfinales();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        networkData.observeForever(newAlimentosFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Insert our new alimentos into local database
                mAlimjsonDao.insertarAlimento(Arrays.asList(newAlimentosFromNetwork));
                Log.d(LOG_TAG, "New values inserted in Room");
            });
        });
    }

    public synchronized static AlimentoRepository getInstance(AlimentosNetworkDataSource nds, DaoAlimentojson dao, DaoAlimento daoAlim, DaoComidas daoComidas, DaoAlimentosEnComida daoAlimentosEnComida) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            sInstance = new AlimentoRepository(dao, nds, daoAlim, daoComidas, daoAlimentosEnComida);//Pasa las dependencias por constructor
            Log.d(LOG_TAG, "Made new repository");
        }
        return sInstance;
    }

    public void doFetchAlimentos() {
        Log.d(LOG_TAG, "Fetching Alimentos from Json");
        AppExecutors.getInstance().diskIO().execute(() -> {
            mAlimjsonDao.eliminaralimentos();
            mAlimNetworkDataSource.fetchAlimentos();
        });
    }

    //Devuelve Todos los alimentos que nos da la API, no hace falta transformación porque no recibe ningún parámetro
    public LiveData<List<AlimentosFinales>> getcurrentalimentos() {
        return mAlimjsonDao.getalimentosfinales();
    }


    //Método para poner las fechas, sirve para todos los fragments donde se le llame
    public void setFechas(Long f1,Long f2) {
       userFilterLiveData1.setValue(f1);
        userFilterLiveData2.setValue(f2);
    }


/*
*
* *MÉTODOS UTILIZADOS EN HISTORIAL
*
 */
public  LiveData<Integer> getcaloriastotales(){
    return Transformations.switchMap(userFilterLiveData1, new Function<Long, LiveData<Integer>>() {
        @Override
        public LiveData<Integer> apply(Long input) {
            return Transformations.switchMap(userFilterLiveData2, new Function<Long, LiveData<Integer>>() {
                @Override
                public LiveData<Integer> apply(Long input2) {
                    return mAlimentos.getAllcaloriasfecha(input, input2);
                }
            });
        }
    });

}


    public LiveData<List<Alimento>> getcurrentalimentosconsumidos() {
        return Transformations.switchMap(userFilterLiveData1, new Function<Long, LiveData<List<Alimento>>>() {
            @Override
            public LiveData<List<Alimento>> apply(Long input) {
                return Transformations.switchMap(userFilterLiveData2, new Function<Long, LiveData<List<Alimento>>>() {
                    @Override
                    public LiveData<List<Alimento>> apply(Long input2) {
                        return mAlimentos.getAllfechalivedata(input, input2);
                    }
                });
            }
        });
    }

//Métodos utilizados en fragment desayuno,comida y cena
    public  LiveData<Integer> getcaloriastotalescomidas(String tipo){
        return Transformations.switchMap(userFilterLiveData1, new Function<Long, LiveData<Integer>>() {
            @Override
            public LiveData<Integer> apply(Long input) {
                return Transformations.switchMap(userFilterLiveData2, new Function<Long, LiveData<Integer>>() {
                    @Override
                    public LiveData<Integer> apply(Long input2) {
                        return mAlimentos.getcaloriastotalescomidas(tipo,input, input2);
                    }
                });
            }
        });
    }

    public LiveData<List<Alimento>> getcurrentalimentosconsumidos(String tipo) {
        return Transformations.switchMap(userFilterLiveData1, new Function<Long, LiveData<List<Alimento>>>() {
            @Override
            public LiveData<List<Alimento>> apply(Long input) {
                return Transformations.switchMap(userFilterLiveData2, new Function<Long, LiveData<List<Alimento>>>() {
                    @Override
                    public LiveData<List<Alimento>> apply(Long input2) {
                        return mAlimentos.getAlldiariascomidas(tipo,input, input2);
                    }
                });
            }
        });
    }


    public void insertaralimentos(Alimento aliment,Comida comida){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                long  id_alim=  mAlimentos.addalimento(aliment);
                long id_comida= mComidas.addcomida(comida);
                AlimentoEnComida alimencomida = new AlimentoEnComida(id_alim, id_comida);
                mAlimentosEnComidas.addalimcomida(alimencomida);
            }

    });
    }

    public void eliminaralimentos(long id_alim){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Alimento alimento= mAlimentos.obteneralimento(id_alim);
                mAlimentos.deletealimento(alimento);
                long idcomida=  mComidas.obteneridcomida(id_alim);
                List<Comida> comidas =  mComidas.obtenercomidas(idcomida);
                List<AlimentoEnComida> alimencomidas=   mAlimentosEnComidas.obteneralimentos(id_alim,idcomida);
                for (int j = 0; j <comidas.size() ; j++) {
                    mComidas.deletecomida(comidas.get(j));
                }
                for (int j = 0; j <comidas.size() ; j++) {
                    mAlimentosEnComidas.deletealimentoencomida(alimencomidas.get(j));
                }


            }
        });
    }


}




