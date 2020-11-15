package com.example.fithealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
}