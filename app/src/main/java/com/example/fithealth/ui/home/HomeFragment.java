package com.example.fithealth.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fithealth.MyAdapter;
import com.example.fithealth.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements MyAdapter.OnListInteractionListener {

    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.listadoElem);
        assert(recyclerView)!=null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(new ArrayList<>(),this);
            AppExecutors.getInstance().networkIO().execute(new AlimentosNetworkLoaderRunnable(alimentos -> mAdapter.swap(alimentos)));
recyclerView.setAdapter(mAdapter);

        /*


        service.getAlimentos("region").enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                List<Food> alimentos = response.body();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.swap(alimentos);
                    }
                });

            }
            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        */

/*
        List<Alimentos> aliments;
        com.google.gson.stream.JsonReader reader = new JsonReader(new InputStreamReader(getResources().openRawResource(R.raw.alimentos)));
        try {
            aliments = Arrays.asList(new Gson().fromJson(reader, Alimentos[].class));
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mAdapter = new MyAdapter(aliments,  this);
        recyclerView.setAdapter(mAdapter);



            Spinner spinner = (Spinner) root.findViewById(R.id.spinner2);
        String[] valores = {"uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, valores);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //    Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio

            }
        });
        */
        return root;
    }

    @Override
    public void onListInteraction(String nombre) {
        Uri webpage = Uri.parse(nombre);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }
}
