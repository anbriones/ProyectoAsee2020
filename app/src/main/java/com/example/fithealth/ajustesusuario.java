package com.example.fithealth;

import android.content.Context;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

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


    }


    }











