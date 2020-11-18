package com.example.fithealth.ui.cena;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;




import com.example.fithealth.AdapterBaseDatos;


import com.example.fithealth.MyAdapterJson;
import com.example.fithealth.R;
import com.example.fithealth.database.Alimento;
import com.example.fithealth.database.Alimento.Tipo;
import com.example.fithealth.database.AlimentosDataBase;
import com.example.fithealth.lecturaJson.AlimentosAna;
import com.example.fithealth.ui.lecturaAPI.AppExecutors;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CenaFragment extends Fragment implements MyAdapterJson.OnListInteractionListener, AdapterBaseDatos.OnListInteractionListener{

    private NotificationsViewModel notificationsViewModel;




        private RecyclerView recyclerView;
        private MyAdapterJson mAdapter;
        private RecyclerView.LayoutManager layoutManager;

        private AdapterBaseDatos mAdapter2;
        private RecyclerView recyclerView2;
        private RecyclerView.LayoutManager layoutManager2;
        private Alimento alimento;
        private static final int ADD_TODO_ITEM_REQUEST = 0;
        private Date mdate;

        public View onCreateView(@NonNull LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            notificationsViewModel =
                    new ViewModelProvider(this).get(NotificationsViewModel.class);
            View root = inflater.inflate(R.layout.fragment_cena, container, false);

            recyclerView = (RecyclerView) root.findViewById(R.id.listadoElemcena);
            assert (recyclerView) != null;
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);

            List<AlimentosAna> aliments;
            com.google.gson.stream.JsonReader reader = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.alimentos)));
            try {
                aliments = Arrays.asList(new Gson().fromJson(reader, AlimentosAna[].class));
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            mAdapter = new MyAdapterJson(aliments, this::onListInteraction);
            recyclerView.setAdapter(mAdapter);


            recyclerView2 = (RecyclerView) root.findViewById(R.id.escogidoscena);
            assert (recyclerView2) != null;
            recyclerView2.setHasFixedSize(true);
            layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView2.setLayoutManager(layoutManager2);
            mAdapter2=new AdapterBaseDatos(getActivity(), this::onListInteractionBD);

            recyclerView2.setAdapter(mAdapter2);

            return root;
        }


    public long fechaactual(){
        Date    mDate = new Date();
        mDate = new Date(mDate.getTime() );

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        Integer  dia=c.get(Calendar.DAY_OF_MONTH);
        Integer mes=c.get(Calendar.MONTH);
        Integer año=c.get(Calendar.YEAR);

        GregorianCalendar gc = new GregorianCalendar(año,mes,dia);

        return  gc.getTimeInMillis();
    }
    public long fechaactualmas1(){
        Date    mDate = new Date();
        mDate = new Date(mDate.getTime() );

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        Integer  dia=c.get(Calendar.DAY_OF_MONTH)+1;
        Integer mes=c.get(Calendar.MONTH);
        Integer año=c.get(Calendar.YEAR);

        GregorianCalendar gc = new GregorianCalendar(año,mes,dia);

        return  gc.getTimeInMillis();
    }

        @Override
        public void onListInteraction(String nombre, Integer calorias, Integer cantidad, String unidad) {
         Date    mDate = new Date();
            mDate = new Date(mDate.getTime() );

            Calendar c = Calendar.getInstance();
            c.setTime(mDate);
            alimento = new Alimento(nombre, calorias, cantidad, unidad, Tipo.cena,c.getTime());
long f1=fechaactual();
long f2=fechaactualmas1();
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    AlimentosDataBase.getInstance(getActivity()).daoAlim().addalimento(alimento);
                    getActivity().runOnUiThread(() -> mAdapter2.add(alimento));
                    final Integer calories = AlimentosDataBase.getInstance(getActivity()).daoAlim().getcaloriastotales("cena",f1,f2);

                        TextView text = getActivity().findViewById(R.id.totalcena);
                        getActivity().runOnUiThread(() -> text.setText(calories.toString()));

                }

            });
        }


        @Override
        public void onResume() {

            super.onResume();
            if (mAdapter2.getItemCount() == 0) {
                loadAlimentos();
            }

            if (mAdapter2.getItemCount() == 0){
                long f1=fechaactual();
                long f2=fechaactualmas1();
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final Integer calories = AlimentosDataBase.getInstance(getActivity()).daoAlim().getcaloriastotales("cena",f1,f2);
                        if (calories != null) {
                            TextView text = getActivity().findViewById(R.id.totalcena);
                            getActivity().runOnUiThread(() -> text.setText(calories.toString()));
                        }
                    }
                });

            }

        }

        @Override
        public void onPause() {
            super.onPause();
          // onSaveInstanceState(savedInstanceState);

             }

        @Override
        public void onDestroy() {
          // AlimentosDataBase.getInstance(getActivity().getApplicationContext()).close();
            super.onDestroy();
        }

        private void loadAlimentos() {

            Date    mDate = new Date();
            mDate = new Date(mDate.getTime() );

            Calendar c = Calendar.getInstance();
            c.setTime(mDate);

           Integer  dia=c.get(Calendar.DAY_OF_MONTH);
           Integer mes=c.get(Calendar.MONTH);
            Integer año=c.get(Calendar.YEAR);

            Integer diasmas=dia+1;

            GregorianCalendar gc = new GregorianCalendar(año,mes,dia);
            GregorianCalendar gc2 = new GregorianCalendar(año,mes,diasmas);


            long timeStamp = gc.getTimeInMillis();
            long timeStamp2 = gc2.getTimeInMillis();

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    List<Alimento> items = AlimentosDataBase.getInstance(getActivity()).daoAlim().getAlldiarias("cena",timeStamp,timeStamp2);

                    getActivity().runOnUiThread(() -> mAdapter2.load(items));

                }
            });
              }

    @Override
    public void onListInteractionBD(Alimento alim) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
               AlimentosDataBase.getInstance(getActivity()).daoAlim().deletealimento(alim);
                getActivity().runOnUiThread(() -> mAdapter2.eliminaralimento(alim));

            }
        });
    }
    }

