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
import com.example.fithealth.AppExecutors;
import com.example.fithealth.MyAdapterJson;
import com.example.fithealth.R;
import com.example.fithealth.datos.lecturaapi.AlimentosNetworkLoaderRunnable;
import com.example.fithealth.datos.model.AlimentosFinales;
import com.example.fithealth.datos.model.Alimento;
import com.example.fithealth.datos.model.AlimentoEnComida;
import com.example.fithealth.datos.model.Comida;
import com.example.fithealth.datos.roomdatabase.Comidasdatabase;

import java.util.ArrayList;
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
        View root = inflater.inflate(R.layout.fragment_comidas, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.listadoElemcena);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new MyAdapterJson(new ArrayList<AlimentosFinales>(), this);
         AppExecutors.getInstance().networkIO().execute(new AlimentosNetworkLoaderRunnable((aliments) -> mAdapter.swap(aliments)));
        recyclerView.setAdapter(mAdapter);


        recyclerView2 = (RecyclerView) root.findViewById(R.id.escogidoscena);
        assert (recyclerView2) != null;
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        mAdapter2 = new AdapterBaseDatos(getActivity().getApplicationContext(), this::onListInteractionBD);

        recyclerView2.setAdapter(mAdapter2);


        return root;
    }

    public long fechaactual() {
        Date mDate = new Date();
        mDate = new Date(mDate.getTime());

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        Integer dia = c.get(Calendar.DAY_OF_MONTH);
        Integer mes = c.get(Calendar.MONTH);
        Integer año = c.get(Calendar.YEAR);

        GregorianCalendar gc = new GregorianCalendar(año, mes, dia);

        return gc.getTimeInMillis();
    }

    public long fechaactualmas1() {
        Date mDate = new Date();
        mDate = new Date(mDate.getTime());

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        Integer dia = c.get(Calendar.DAY_OF_MONTH) + 1;
        Integer mes = c.get(Calendar.MONTH);
        Integer año = c.get(Calendar.YEAR);

        GregorianCalendar gc = new GregorianCalendar(año, mes, dia);

        return gc.getTimeInMillis();
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


        long f1 = fechaactual();
        long f2 = fechaactualmas1();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Long id_alim = Comidasdatabase.getInstance(getActivity()).daoAlim().addalimento(aliment);
                Long id_comida = Comidasdatabase.getInstance(getActivity()).daoComidas().addcomida(comida);
                AlimentoEnComida alimencomida = new AlimentoEnComida(id_alim, id_comida);
                Comidasdatabase.getInstance(getActivity()).daoAlimentosEnComida().addalimcomida(alimencomida);
                getActivity().runOnUiThread(() -> mAdapter2.add(aliment));

                    loadAlimentos();
                final Integer calories = Comidasdatabase.getInstance(getActivity().getApplicationContext()).daoAlim().getcaloriastotales("desayuno", f1, f2);
                if (calories != 0) {
                    TextView text = getActivity().findViewById(R.id.totalcena);
                    getActivity().runOnUiThread(() -> text.setText(calories.toString()));
                }
            }

        });
    }



    @Override
    public void onResume() {

        super.onResume();

        if (mAdapter2.getItemCount() == 0)
            loadAlimentos();
calcularcalorias();
    }

    public void calcularcalorias() {
        long f1 = fechaactual();
        long f2 = fechaactualmas1();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final Integer calories = Comidasdatabase.getInstance(getActivity().getApplicationContext()).daoAlim().getcaloriastotales("desayuno", f1, f2);
                if (calories != null) {
                    TextView text = getActivity().findViewById(R.id.totalcena);
                    getActivity().runOnUiThread(() -> text.setText(calories.toString()));
                }
                else{
                    TextView text = getActivity().findViewById(R.id.totalcena);
                    getActivity().runOnUiThread(() -> text.setText("0 nada"));
                }
            }
        });

    }


    @Override
    public void onPause() {
        super.onPause();
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
                    List<Alimento> items = Comidasdatabase.getInstance(getActivity().getApplicationContext()).daoAlim().getAlldiarias("desayuno", timeStamp, timeStamp2);
                    getActivity().runOnUiThread(() -> mAdapter2.load(items));

                }
            });
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

                loadAlimentos();
                calcularcalorias();
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


