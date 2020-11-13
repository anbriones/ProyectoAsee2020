package com.example.fithealth.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.AdapterBaseDatos;
import com.example.fithealth.MyAdapter;
import com.example.fithealth.R;
import com.example.fithealth.database.Alimento;
import com.example.fithealth.database.AlimentosDataBase;
import com.example.fithealth.model.AlimentosAna;
import com.example.fithealth.ui.Desayuno.AppExecutors;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment implements MyAdapter.OnListInteractionListener {
    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private AdapterBaseDatos mAdapter2;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;

    private static final int ADD_TODO_ITEM_REQUEST = 0;

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

        mAdapter = new MyAdapter(aliments, this);
        recyclerView.setAdapter(mAdapter);


        recyclerView2 = (RecyclerView) root.findViewById(R.id.escogidos);
        assert (recyclerView2) != null;
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        mAdapter2=new AdapterBaseDatos(getActivity());

        recyclerView2.setAdapter(mAdapter2);
        //Al ser un singleton solo se le llama una vez
        AlimentosDataBase.getInstance(this.getActivity());

        return root;
    }


    @Override
    public void onListInteraction(String nombre, Integer calorias, Integer cantidad, String unidad) {
        Alimento aliment = new Alimento(nombre, calorias, cantidad, unidad);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AlimentosDataBase.getInstance(getActivity()).daoAlim().addalimento(aliment);

            getActivity().runOnUiThread(() ->     mAdapter2.add(aliment));
                }
        });

        }

    @Override
    public void onResume() {
        new ViewModelProvider(this).get(HomeViewModel.class);
        super.onResume();
        if (mAdapter2.getItemCount() == 0)
            loadAlimentos();
    }


    private void loadAlimentos() {
        // ToDoItemCRUD crud = ToDoItemCRUD.getInstance(this);
        // List<ToDoItem> items = crud.getAll();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List < Alimento > items = AlimentosDataBase.getInstance(getActivity()).daoAlim().getAll();
                getActivity().runOnUiThread(()->mAdapter2.load(items));
            }
        });


    }
}