package com.example.fithealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


    }


    }











