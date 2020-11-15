package com.example.fithealth.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.AdapterBaseDatosDesayuno;
import com.example.fithealth.MyAdapterDesayuno;
import com.example.fithealth.R;
import com.example.fithealth.database.Alimento;
import com.example.fithealth.database.AlimentosDataBase;
import com.example.fithealth.model.AlimentosAna;
import com.example.fithealth.ui.Desayuno.AppExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements MyAdapterDesayuno.OnListInteractionListener {
    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;
    private MyAdapterDesayuno mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private AdapterBaseDatosDesayuno mAdapter2;
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

        mAdapter = new MyAdapterDesayuno(aliments, this);
        recyclerView.setAdapter(mAdapter);


        recyclerView2 = (RecyclerView) root.findViewById(R.id.escogidos);
        assert (recyclerView2) != null;
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        mAdapter2 = new AdapterBaseDatosDesayuno(getActivity());

        recyclerView2.setAdapter(mAdapter2);
        //Al ser un singleton solo se le llama una vez
        AlimentosDataBase.getInstance(this.getActivity());



        return root;
    }


    @Override
    public void onListInteraction(String nombre, Integer calorias, Integer cantidad, String unidad) {
        Alimento.Tipo tipo= Alimento.Tipo.valueOf("desayuno");
        Alimento aliment = new Alimento(nombre, calorias, cantidad, unidad,tipo, Calendar.getInstance().getTime());
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AlimentosDataBase.getInstance(getActivity()).daoAlim().addalimento(aliment);
                getActivity().runOnUiThread(() -> mAdapter2.add(aliment));
                final Integer calories = AlimentosDataBase.getInstance(getActivity()).daoAlim().getcaloriastotales("desayuno");
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
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final Integer calories = AlimentosDataBase.getInstance(getActivity()).daoAlim().getcaloriastotales("desayuno");
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
    }

    private void loadAlimentos() {

        // ToDoItemCRUD crud = ToDoItemCRUD.getInstance(this);
        // List<ToDoItem> items = crud.getAll();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Alimento> items = AlimentosDataBase.getInstance(getActivity()).daoAlim().getAll("desayuno");
                getActivity().runOnUiThread(() -> mAdapter2.load(items));
              }
        });


    }






    }
