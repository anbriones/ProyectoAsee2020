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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Historial extends AppCompatActivity {
    private static final int ADD_TODO_ITEM_REQUEST = 0;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;
    private  Adaptercomidas adapter;
    private static String datestring;
    private static String datestringm1;
    private static Long dateLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    datestring="";
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

            setDateString(year,month,dayOfMonth);




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

        cargaralimentos();


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
        datestring="";
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        String monm = "" + monthOfYear;
        String daym = "" + dayOfMonth;

        if (monthOfYear < 10)
            monm = "0" + monthOfYear+1;
        if (dayOfMonth < 10)
            daym = "0" + dayOfMonth+1;


        datestring  = year + "-" + mon + "-" + day;
        datestringm1  = year + "-" + monm + "-" + daym;
    }


    public void cargaralimentos(){
        if(!datestring.isEmpty()) {
            long l = Long.parseLong(datestring);
            long l2 = Long.parseLong(datestringm1);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    List<Alimento> items = AlimentosDataBase.getInstance(Historial.this).daoAlim().getAllfecha(l, l2);
                    Historial.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.load(items);
                        }
                    });
                }
            });
        }
        // Load saved ToDoItems, if necessary

    }

    }


