package com.example.fithealth;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fithealth.database.Alimento;
import com.example.fithealth.database.AlimentosDataBase;
import com.example.fithealth.database.DateConverter;
import com.example.fithealth.ui.Desayuno.AppExecutors;
import com.example.fithealth.ui.home.HomeViewModel;

import java.util.Date;
import java.util.List;

public class Historial extends AppCompatActivity {
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;
    private  Adaptercomidas adapter;
   // private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial);


        recyclerView2 = (RecyclerView) findViewById(R.id.escogidos);
        assert (recyclerView2) != null;
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
          adapter = new Adaptercomidas(this);

        recyclerView2.setAdapter(adapter);
        //Al ser un singleton solo se le llama una vez
        AlimentosDataBase.getInstance(this);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendario);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                // Increment monthOfYear for Calendar/Date -> Time Format setting
                month++;
                String mon = "" + month;
                String day = "" + dayOfMonth;

                if (month < 10)
                    mon = "0" + month;
                if (dayOfMonth < 10)
                    day = "0" + dayOfMonth;


             Toast.makeText(getApplicationContext(), "" + dayOfMonth, 0).show();// TODO Auto-generated method stub


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

        if (adapter.getItemCount() == 0) {
            cargaralimentos();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        // ALTERNATIVE: Save all ToDoItems

    }

    @Override
    protected void onDestroy() {
        /// ToDoItemCRUD crud = ToDoItemCRUD.getInstance(this);
        //  crud.close();
        AlimentosDataBase.getInstance(this).close();
        super.onDestroy();
    }


    public void cargaralimentos(){/*
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Alimento> items = AlimentosDataBase.getInstance(Historial.this).daoAlim().getAllFecha(date,date);
                Historial.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.load(items);
                    }
                });
            }
        });*/
        // Load saved ToDoItems, if necessary

    }

    }


