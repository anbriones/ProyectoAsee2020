package com.example.fithealth.ui.cena;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.AdapterBAseDatosCena;


import com.example.fithealth.MyAdapterCena;

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

public class CenaFragment extends Fragment implements MyAdapterCena.OnListInteractionListener{

    private NotificationsViewModel notificationsViewModel;




        private RecyclerView recyclerView;
        private MyAdapterCena mAdapter;
        private RecyclerView.LayoutManager layoutManager;

        private AdapterBAseDatosCena mAdapter2;
        private RecyclerView recyclerView2;
        private RecyclerView.LayoutManager layoutManager2;

        private static final int ADD_TODO_ITEM_REQUEST = 0;
        private Object Date;

        public View onCreateView(@NonNull LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState) {
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

            mAdapter = new MyAdapterCena(aliments, this::onListInteraction);
            recyclerView.setAdapter(mAdapter);


            recyclerView2 = (RecyclerView) root.findViewById(R.id.escogidoscena);
            assert (recyclerView2) != null;
            recyclerView2.setHasFixedSize(true);
            layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView2.setLayoutManager(layoutManager2);
            mAdapter2=new AdapterBAseDatosCena(getActivity());

            recyclerView2.setAdapter(mAdapter2);
            //Al ser un singleton solo se le llama una vez
            AlimentosDataBase.getInstance(this.getActivity());

            return root;
        }


        @Override
        public void onListInteraction(String nombre, Integer calorias, Integer cantidad, String unidad) {
            Calendar calendar = Calendar.getInstance();

            Alimento.Tipo tipo= Alimento.Tipo.valueOf("cena");

            Alimento aliment = new Alimento(nombre, calorias, cantidad, unidad,tipo, Calendar.getInstance().getTime());

         aliment.setTipo(Alimento.Tipo.cena);


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
            recyclerView.setAdapter(mAdapter);
            super.onResume();
            if (mAdapter2.getItemCount() == 0)
                loadAlimentos();
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final Integer calories = AlimentosDataBase.getInstance(getActivity()).daoAlim().getcaloriastotales("cena");
                    TextView text=getActivity().findViewById(R.id.totalcena);
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
          //  AlimentosDataBase.getInstance(getActivity().getApplicationContext()).close();
            super.onDestroy();
        }

        private void loadAlimentos() {
            // ToDoItemCRUD crud = ToDoItemCRUD.getInstance(this);
            // List<ToDoItem> items = crud.getAll();
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    List < Alimento > items = AlimentosDataBase.getInstance(getActivity()).daoAlim().getAll("cena");
                    getActivity().runOnUiThread(()->mAdapter2.load(items));
                }
            });




        }
    }