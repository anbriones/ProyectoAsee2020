package com.example.fithealth.ui.desayuno;

import android.os.Bundle;
import android.util.Log;
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
import com.example.fithealth.AppContainer;
import com.example.fithealth.AppExecutors;
import com.example.fithealth.InjectorUtils;
import com.example.fithealth.MyAdapterJson;
import com.example.fithealth.MyApplication;
import com.example.fithealth.R;
import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.model.Alimento;
import com.example.fithealth.datos.model.AlimentoEnComida;
import com.example.fithealth.datos.model.AlimentosFinales;
import com.example.fithealth.datos.model.Comida;
import com.example.fithealth.datos.roomdatabase.Comidasdatabase;
import com.example.fithealth.ui.FechasActual;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements MyAdapterJson.OnListInteractionListener, AdapterBaseDatos.OnListInteractionListener{
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_comidas, container, false);
        TextView textcalorias = root.findViewById(R.id.totalcena);
       recyclerView = (RecyclerView) root.findViewById(R.id.listadoElemcena);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapterJson(new ArrayList<AlimentosFinales>(), this);
        recyclerView.setAdapter(mAdapter);
        HomeViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactoryhome(this.getActivity().getApplicationContext());
        AppContainer appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;

        HomeViewModel mViewModel = new ViewModelProvider(this, appContainer.factoryhome).get(HomeViewModel.class);
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
            AppExecutors.getInstance().mainThread().execute(() -> Log.d(LOG_TAG, "Modificada"));
            mAdapter2.swap(alimentosdesayuno);
        });


        mViewModel.getMcalorias().observe(this.getActivity(), calorias -> {
            AppExecutors.getInstance().mainThread().execute(() -> Log.d(LOG_TAG, "calorias nuevas"));
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


        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Long id_alim = Comidasdatabase.getInstance(getActivity()).daoAlim().addalimento(aliment);
                Long id_comida = Comidasdatabase.getInstance(getActivity()).daoComidas().addcomida(comida);
                AlimentoEnComida alimencomida = new AlimentoEnComida(id_alim, id_comida);
                Comidasdatabase.getInstance(getActivity()).daoAlimentosEnComida().addalimcomida(alimencomida);
                getActivity().runOnUiThread(() -> mAdapter2.add(aliment));
            }

        });


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
    public void onListInteractionBD(long alim) {
        //   long idalim= alim.getId();
        String id=Long.toString(alim);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Alimento alimento= Comidasdatabase.getInstance(getActivity()).daoAlim().obteneralimento(alim);
                getActivity().runOnUiThread(() -> mAdapter2.eliminaralimento(alimento));
                Comidasdatabase.getInstance(getActivity()).daoAlim().deletealimento(alimento);

                long idcomida=   Comidasdatabase.getInstance(getActivity()).daoComidas().obteneridcomida(alim);
                List<Comida> comidas =  Comidasdatabase.getInstance(getActivity()).daoComidas().obtenercomidas( idcomida);

                List<AlimentoEnComida> alimencomidas=   Comidasdatabase.getInstance(getActivity()).daoAlimentosEnComida().obteneralimentos(alim,idcomida);
                for (int i = 0; i < comidas.size(); i++) {
                    Comidasdatabase.getInstance(getActivity()).daoComidas().deletecomida(comidas.get(i));
                }
                for (int i = 0; i < alimencomidas.size(); i++) {
                    Comidasdatabase.getInstance(getActivity()).daoAlimentosEnComida().deletealimentoencomida(alimencomidas.get(i));
                }



            }
        });



    }

}