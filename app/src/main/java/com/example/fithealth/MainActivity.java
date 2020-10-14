package com.example.fithealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }
}