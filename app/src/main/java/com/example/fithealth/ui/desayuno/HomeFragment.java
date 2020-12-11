package com.example.fithealth.ui.desayuno;

import android.content.Intent;
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

import com.example.fithealth.AdapterBaseDatos;
import com.example.fithealth.AppContainer;
import com.example.fithealth.InjectorUtils;
import com.example.fithealth.MyAdapterJson;
import com.example.fithealth.MyApplication;
import com.example.fithealth.R;
import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.model.Alimento;
import com.example.fithealth.datos.model.AlimentosFinales;
import com.example.fithealth.datos.model.Comida;
import com.example.fithealth.detallesAlimento;
import com.example.fithealth.ui.FechasActual;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment implements MyAdapterJson.OnListInteractionListener,MyAdapterJson.OnListInteractionListenerdetalle, AdapterBaseDatos.OnListInteractionListener{
    private static final String LOG_TAG = HomeFragment.class.getSimpleName();
    private HomeViewModel homeViewModel;

    private AlimentoRepository mRepository;
    private RecyclerView recyclerView;
    private MyAdapterJson mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private AdapterBaseDatos mAdapter2;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;
    private FechasActual fechas=new FechasActual();//Esta clase la he creado para que no haya duplicidad de código, puesto que los otros fragents voy a necesitarla también
    AppContainer appContainer;
    HomeViewModel mViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_comidas, container, false);


        TextView textcalorias = root.findViewById(R.id.totalcena);
       recyclerView = (RecyclerView) root.findViewById(R.id.listadoElemcena);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapterJson(new ArrayList<AlimentosFinales>(), this,this);
        recyclerView.setAdapter(mAdapter);
        HomeViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactoryhome(this.getActivity().getApplicationContext());
        appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, appContainer.factoryhome).get(HomeViewModel.class);
        mViewModel.getMalimentosfinales().observe(this.getActivity(), alimentos -> {
            mAdapter.swap(alimentos);
        });

        recyclerView2 = (RecyclerView) root.findViewById(R.id.escogidoscena);
        assert (recyclerView2) != null;
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        mAdapter2 = new AdapterBaseDatos(getActivity().getApplicationContext(), this::onListInteractionBD);
        recyclerView2.setAdapter(mAdapter2);


        mViewModel.setfecha(fechas.getf1(),fechas.getf2());
        mViewModel.getMalimentos().observe(this.getActivity(), alimentosdesayuno -> {
                        mAdapter2.swap(alimentosdesayuno);
        });

        mViewModel.getMcalorias().observe(this.getActivity(), calorias -> {
            if (calorias != null) {
                textcalorias.setText(calorias.toString());
            } else {
                textcalorias.setText("0 ");
            }
        });


            return root;
        }



    @Override
    public void onListInteraction(String nombre, Integer calorias, Integer cantidad, String unidad) {
        Date mDate = new Date();
        mDate = new Date(mDate.getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        mDate = c.getTime();

        Alimento aliment = new Alimento(nombre, calorias, cantidad, unidad);
        Comida.Tipo tipo = Comida.Tipo.valueOf("desayuno");
        Comida comida = new Comida(tipo, mDate);

        mViewModel = new ViewModelProvider(this, appContainer.factoryhome).get(HomeViewModel.class);

        mViewModel.insertarAlimentos(aliment,comida);



    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
            }

    @Override
    public void onListInteractionBD(long alim,String nombre) {
         mViewModel = new ViewModelProvider(this, appContainer.factoryhome).get(HomeViewModel.class);
        Toast toast1 =
                Toast.makeText(getActivity().getApplicationContext(),
                        "Se ha eliminado "+nombre, Toast.LENGTH_SHORT);
        toast1.show();
        mViewModel.eliminarAlimentos(alim);



    }

    @Override
    public void onListInteraction2(AlimentosFinales alim) {
        Intent intentdetalles = new Intent(this.getActivity(), detallesAlimento.class);
        intentdetalles.putExtra("alimentos", (Serializable) alim);
        startActivity(intentdetalles);
    }

}