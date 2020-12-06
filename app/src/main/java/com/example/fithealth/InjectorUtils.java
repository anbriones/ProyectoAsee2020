package com.example.fithealth;

import android.content.Context;

import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.lecturaapi.AlimentosNetworkDataSource;
import com.example.fithealth.datos.roomdatabase.Comidasdatabase;
import com.example.fithealth.ui.Historialcomidas.HistorialViewModelFactory;
import com.example.fithealth.ui.cena.CenaViewModelFactory;
import com.example.fithealth.ui.comida.ComidaViewModelFactory;
import com.example.fithealth.ui.desayuno.HomeViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for the app
 */
public class InjectorUtils {

    public static AlimentoRepository provideRepository(Context context) {
        Comidasdatabase database = Comidasdatabase.getInstance(context.getApplicationContext());//Base de datos
        AlimentosNetworkDataSource networkDataSource = AlimentosNetworkDataSource.getInstance();//Fuente de datos remota
        return AlimentoRepository.getInstance(networkDataSource,database.daoAlimentojson(),database.daoAlim(),database.daoComidas(),database.daoAlimentosEnComida());
    }

    public static HomeViewModelFactory provideMainActivityViewModelFactoryhome(Context context) {
        AlimentoRepository repository = provideRepository(context.getApplicationContext());
        return new HomeViewModelFactory(repository);
    }

    public static ComidaViewModelFactory provideMainActivityViewModelFactorycomida(Context context) {
        AlimentoRepository repository = provideRepository(context.getApplicationContext());
        return new ComidaViewModelFactory(repository);
    }

    public static CenaViewModelFactory provideMainActivityViewModelFactorycena(Context context) {
        AlimentoRepository repository = provideRepository(context.getApplicationContext());
        return new CenaViewModelFactory(repository);
    }
    public static HistorialViewModelFactory provideMainActivityViewModelFactoryhistorial(Context context) {
        AlimentoRepository repository = provideRepository(context.getApplicationContext());
        return new HistorialViewModelFactory(repository);
    }
}