package com.example.fithealth;

import android.app.Application;

public class MyApplication extends Application {
    public AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        //Se  crea aquí porque es necesario pasar el contexto para las bases de datos de room
        appContainer = new AppContainer(this);
    }
}