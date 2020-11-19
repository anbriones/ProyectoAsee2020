package com.example.fithealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    public static final String KEY_PREF_ALTURA = "alturak";
    public static final String KEY_PREF_PESO = "pesok";
    public static final String KEY_PREF_SEXO = "Sexok";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
    //Text view de la fecha
        Date myDate = new Date();
        String myString = DateFormat.getDateInstance().format(myDate);
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);
        try {
            myDate = df.parse(myString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView textView=findViewById(R.id.fecha);
        textView.setText(myString);

        ImageButton usuario = findViewById(R.id.userimage);
        usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Usuario.class);
                startActivity(intent);
            }
        });

        ImageButton historical = findViewById(R.id.historial);
        historical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenthistorial = new Intent(MainActivity.this, Historial.class);
                startActivity(intenthistorial);
            }
        });


        ImageButton diariob = findViewById(R.id.diario);
        diariob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentdiario = new Intent(MainActivity.this, Diario.class);
                startActivity(intentdiario);
            }
        });



    }

    public Double calcularcalorias(){
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);

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

        return caloriasnecesarias;
    }
    @Override
    public void onResume() {
        super.onResume();
Double cal=calcularcalorias();
        TextView calorias=findViewById(R.id.caloriasmain);
        calorias.setText(String.format("%.2f",cal));
    }
}