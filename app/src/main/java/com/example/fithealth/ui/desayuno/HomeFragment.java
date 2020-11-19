package com.example.fithealth.ui.desayuno;

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
import com.example.fithealth.database.AlimentosDataBase;

import com.example.fithealth.lecturaJson.AlimentosFinales;
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

public class HomeFragment extends Fragment implements MyAdapterJson.OnListInteractionListener, AdapterBaseDatos.OnListInteractionListener {
    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;
    private MyAdapterJson mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private AdapterBaseDatos mAdapter2;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.listadoElem);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        List<AlimentosFinales> aliments;
        com.google.gson.stream.JsonReader reader = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.alimentoscompletos)));
        try {
            aliments = Arrays.asList(new Gson().fromJson(reader, AlimentosFinales[].class));
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mAdapter = new MyAdapterJson(aliments, this);
        recyclerView.setAdapter(mAdapter);


        recyclerView2 = (RecyclerView) root.findViewById(R.id.escogidoscalendar);
        assert (recyclerView2) != null;
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        mAdapter2 = new AdapterBaseDatos(getActivity(),this::onListInteractionBD);

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
        Alimento.Tipo tipo= Alimento.Tipo.valueOf("desayuno");
        Date    mDate = new Date();
        mDate = new Date(mDate.getTime() );

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        mDate=c.getTime();
        Alimento aliment = new Alimento(nombre, calorias, cantidad, unidad,tipo, mDate);

        long f1=fechaactual();
        long f2=fechaactualmas1();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AlimentosDataBase.getInstance(getActivity()).daoAlim().addalimento(aliment);
                getActivity().runOnUiThread(() -> mAdapter2.add(aliment));
                final Integer calories = AlimentosDataBase.getInstance(getActivity()).daoAlim().getcaloriastotales("desayuno",f1,f2);
                if (calories != 0) {
                    TextView text = getActivity().findViewById(R.id.total);
                    getActivity().runOnUiThread(() -> text.setText(calories.toString()));
                }
            }

        });
        }




    @Override
    public void onResume() {
        new ViewModelProvider(this).get(HomeViewModel.class);
        super.onResume();
        if (mAdapter2.getItemCount() == 0)
            loadAlimentos();

        if (mAdapter2.getItemCount() == 0){
            long f1=fechaactual();
            long f2=fechaactualmas1();
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final Integer calories = AlimentosDataBase.getInstance(getActivity()).daoAlim().getcaloriastotales("desayuno",f1,f2);
                    if (calories != null) {
                        TextView text = getActivity().findViewById(R.id.total);
                        getActivity().runOnUiThread(() -> text.setText(calories.toString()));
                    }
                }
            });

        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // ALTERNATIVE: Save all ToDoItems

    }

    @Override
    public void onDestroy() {
               super.onDestroy();
       // AlimentosDataBase.getInstance(getActivity().getApplicationContext()).close();
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
                List<Alimento> items = AlimentosDataBase.getInstance(getActivity()).daoAlim().getAlldiarias("desayuno",timeStamp,timeStamp2);
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
