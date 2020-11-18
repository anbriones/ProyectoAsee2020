package com.example.fithealth;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ajustesusuario extends PreferenceFragmentCompat {

    public static  final String KEY_PREF_ALTURA="alturak";
    public static  final String KEY_PREF_PESO="pesok";
    public static  final String KEY_PREF_SEXO="Sexok";


   

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        Context context = getActivity();
        PreferenceManager.setDefaultValues(context,R.xml.root_preferences,false);
}


    @Override
    public void onResume() {
        super.onResume();
        Context context = getActivity();
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String alturas = preferencias.getString(KEY_PREF_ALTURA, "160");
        String pesos = preferencias.getString(KEY_PREF_PESO, "55");
        String sexos = preferencias.getString(KEY_PREF_SEXO, "Masculino");

        Integer alturafi = Integer.parseInt(alturas);
        Integer pesosf = Integer.parseInt(pesos);


        Double caloriasnecesarias = 0.0;
        if (sexos.equals("Femenino")) {
            caloriasnecesarias = 655 + (9.6 * pesosf) + (1.8 * alturafi) - (4.7 * 30);
        } else if (sexos.equals("Masculino")) {
            caloriasnecesarias = 66 + (13.7 * pesosf) + (5 * alturafi) - (6.75 * 35);
        }

        if(caloriasnecesarias!=null) {
      TextView textcalorias = getActivity().findViewById(R.id.caloriasnecesarias);
     textcalorias.setText(caloriasnecesarias.toString());
}


    }


          }




