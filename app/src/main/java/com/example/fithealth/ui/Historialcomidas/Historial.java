package com.example.fithealth.ui.Historialcomidas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.Adaptercomidashistorial;
import com.example.fithealth.AppContainer;
import com.example.fithealth.AppExecutors;
import com.example.fithealth.InjectorUtils;
import com.example.fithealth.MyApplication;
import com.example.fithealth.R;

import java.util.GregorianCalendar;

public class Historial extends AppCompatActivity {
    private static final String LOG_TAG = Historial.class.getSimpleName();
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;
    private Adaptercomidashistorial adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Para que no se pueda voltear
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial);
        TextView text = Historial.this.findViewById(R.id.totales);
        TextView textcalorias = Historial.this.findViewById(R.id.caloriastotales);

        recyclerView2 = (RecyclerView) findViewById(R.id.escogidoscalendar);
        assert (recyclerView2) != null;
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
        adapter = new Adaptercomidashistorial(this);

        recyclerView2.setAdapter(adapter);
        //Al ser un singleton solo se le llama una vez

        HistorialViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactoryhistorial(getApplicationContext());
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        HistorialViewModel mViewModel = new ViewModelProvider(this, appContainer.factoryhistorial).get(HistorialViewModel.class);
        mViewModel.getMalimentosfinales().observe(this, alimentos -> {
            adapter.swap(alimentos);
            AppExecutors.getInstance().mainThread().execute(() -> Log.d(LOG_TAG, "alimentos de fecha"+alimentos.size()));
           if(alimentos!=null)
            recyclerView2.setVisibility(View.VISIBLE);
           else{
               recyclerView2.setVisibility(View.INVISIBLE);
           }
        });

        HistorialViewModel mViewModel2 = new ViewModelProvider(this, appContainer.factoryhistorial).get(HistorialViewModel.class);
        mViewModel2.getCalorias().observe(this, calorias -> {
                    if (calorias != null) {
                        text.setText(calorias.toString());
                        textcalorias.setText("calorias");
                    }
                    else{
                        text.setText(" ");
                        textcalorias.setText(" ");
                    }
                });


        CalendarView calendarView = (CalendarView) findViewById(R.id.calendario);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
           int day=dayOfMonth+1;
               GregorianCalendar gc = new GregorianCalendar(year,month,dayOfMonth);
                GregorianCalendar gc2 = new GregorianCalendar(year,month,day);
                long timeStamp = gc.getTimeInMillis();
                long timeStamp2 = gc2.getTimeInMillis();

               mViewModel.setfecha(timeStamp,timeStamp2);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // ALTERNATIVE: Save all ToDoItems

    }

    @Override
    protected void onDestroy() {
        //Comidasdatabase.getInstance(this).close();
        super.onDestroy();

    }

    }




