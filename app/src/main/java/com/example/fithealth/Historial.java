package com.example.fithealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.fithealth.roomdatabase.Alimento;
import com.example.fithealth.roomdatabase.Comidasdatabase;
import com.example.fithealth.ui.lecturaAPI.AppExecutors;

import java.util.GregorianCalendar;
import java.util.List;

public class Historial extends AppCompatActivity {
    private static final int ADD_TODO_ITEM_REQUEST = 0;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;
    private Adaptercomidashistorial adapter;
    private static String datestring;
    private static String datestringm1;
    private static Long dateLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    datestring="";
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historial);


        recyclerView2 = (RecyclerView) findViewById(R.id.escogidoscalendar);
        assert (recyclerView2) != null;
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
          adapter = new Adaptercomidashistorial(this);

        recyclerView2.setAdapter(adapter);
        //Al ser un singleton solo se le llama una vez
        Comidasdatabase.getInstance(this);

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



                cargaralimentos(timeStamp,timeStamp2);

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
        Comidasdatabase.getInstance(this).close();
        super.onDestroy();

    }




    public void cargaralimentos(long newDate,long newDate2) {


            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final Integer calories = Comidasdatabase.getInstance(Historial.this).daoAlim().getAllcaloriasfecha(newDate,newDate2);
                    if(calories!=null) {
                        TextView text = Historial.this.findViewById(R.id.totales);
                        Historial.this.runOnUiThread(() -> text.setText(calories.toString()));
                        TextView text2 = Historial.this.findViewById(R.id.caloriastotales);
                        Historial.this.runOnUiThread(() -> text2.setText("calorias"));
                    }
                    List<Alimento> items = Comidasdatabase.getInstance(Historial.this).daoAlim().getAllfecha(newDate,newDate2);
                    Historial.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.load(items);
                        }
                    });
                }
            });


        }
    }




