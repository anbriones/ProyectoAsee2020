package com.example.fithealth;


import android.content.Context;

import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.lecturaapi.AlimentosNetworkDataSource;
import com.example.fithealth.datos.roomdatabase.Comidasdatabase;
import com.example.fithealth.ui.Historialcomidas.HistorialViewModelFactory;
import com.example.fithealth.ui.cena.CenaViewModelFactory;
import com.example.fithealth.ui.comida.ComidaViewModelFactory;
import com.example.fithealth.ui.desayuno.HomeViewModelFactory;


public class AppContainer {

        private Comidasdatabase database;
        private AlimentosNetworkDataSource networkDataSource;
        //Objetos que se necesitan para la ejecución
        public AlimentoRepository repository;
        public HomeViewModelFactory factoryhome;
        public ComidaViewModelFactory factorycomida;
        public CenaViewModelFactory factorycena;
        public HistorialViewModelFactory factoryhistorial;


        public AppContainer(Context context){
            database = Comidasdatabase.getInstance(context);
            networkDataSource = AlimentosNetworkDataSource.getInstance();
            repository = AlimentoRepository.getInstance(networkDataSource,database.daoAlimentojson(),database.daoAlim(),database.daoComidas(),database.daoAlimentosEnComida());
            factoryhome = new HomeViewModelFactory(repository);
            factorycomida = new ComidaViewModelFactory(repository);
            factorycena = new CenaViewModelFactory(repository);
            factoryhistorial = new HistorialViewModelFactory(repository);
        }
    }


