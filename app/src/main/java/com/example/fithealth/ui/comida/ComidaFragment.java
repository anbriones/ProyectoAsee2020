package com.example.fithealth.ui.comida;

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


import com.example.fithealth.AdapterBaseDatosComida;

import com.example.fithealth.MyAdapterComida;
import com.example.fithealth.R;
import com.example.fithealth.database.Alimento;
import com.example.fithealth.database.AlimentosDataBase;
import com.example.fithealth.model.AlimentosAna;
import com.example.fithealth.ui.Desayuno.AppExecutors;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ComidaFragment extends Fragment implements MyAdapterComida.OnListInteractionListener{

    private DashboardViewModel dashboardViewModel;



    private RecyclerView recyclerView;
    private MyAdapterComida mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private AdapterBaseDatosComida mAdapter2;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;

    private static final int ADD_TODO_ITEM_REQUEST = 0;
    private Object Date;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_comida, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.listadoElemcomida);
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

        mAdapter = new MyAdapterComida(aliments, this::onListInteraction);
        recyclerView.setAdapter(mAdapter);


        recyclerView2 = (RecyclerView) root.findViewById(R.id.escogidoscomida);
        assert (recyclerView2) != null;
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        mAdapter2=new AdapterBaseDatosComida(getActivity());

        recyclerView2.setAdapter(mAdapter2);
        //Al ser un singleton solo se le llama una vez
        AlimentosDataBase.getInstance(this.getActivity());

        return root;
    }


    @Override
    public void onListInteraction(String nombre, Integer calorias, Integer cantidad, String unidad) {
        Calendar calendar = Calendar.getInstance();
        Alimento.Tipo tipo= Alimento.Tipo.valueOf("comida");
        Alimento aliment = new Alimento(nombre, calorias, cantidad, unidad,tipo, Calendar.getInstance().getTime());
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AlimentosDataBase.getInstance(getActivity()).daoAlim().addalimento(aliment);
                getActivity().runOnUiThread(() -> mAdapter2.add(aliment));

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter2.getItemCount() == 0)
            loadAlimentos();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final Integer calories = AlimentosDataBase.getInstance(getActivity()).daoAlim().getcaloriastotales("comida");
                TextView text=getActivity().findViewById(R.id.totalc);
                getActivity().runOnUiThread(()-> text.setText(calories.toString()));
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
      //  super.onSaveInstanceState(AlimentosDataBase.getInstance(getActivity().getApplicationContext()));
        // ALTERNATIVE: Save all ToDoItems

    }

    @Override
    public void onDestroy() {

        //AlimentosDataBase.getInstance(getActivity().getApplicationContext()).close();
        super.onDestroy();
    }

    private void loadAlimentos() {
        // ToDoItemCRUD crud = ToDoItemCRUD.getInstance(this);
        // List<ToDoItem> items = crud.getAll();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List < Alimento > items = AlimentosDataBase.getInstance(getActivity()).daoAlim().getAll("comida");
                getActivity().runOnUiThread(()->mAdapter2.load(items));
            }
        });




    }
}