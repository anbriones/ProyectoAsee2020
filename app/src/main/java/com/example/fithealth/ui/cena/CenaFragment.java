package com.example.fithealth.ui.cena;

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
import com.example.fithealth.ui.FechasActual;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CenaFragment extends Fragment implements MyAdapterJson.OnListInteractionListener,MyAdapterJson.OnListInteractionListenerdetalle, AdapterBaseDatos.OnListInteractionListener {
    private CenaViewModel cenaViewModel;

    private AlimentoRepository mRepository;

    private RecyclerView recyclerView;
    private MyAdapterJson mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private AdapterBaseDatos mAdapter2;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager2;
    private FechasActual fechas=new FechasActual();
    AppContainer appContainer;
    CenaViewModel mViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_comidas, container, false);
        TextView text=root.findViewById(R.id.totalcena);
         recyclerView = (RecyclerView) root.findViewById(R.id.listadoElemcena);
        assert (recyclerView) != null;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapterJson(new ArrayList<AlimentosFinales>(), this,this);
        recyclerView.setAdapter(mAdapter);

        CenaViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactorycena(this.getActivity().getApplicationContext());
         appContainer = ((MyApplication) this.getActivity().getApplication()).appContainer;

         mViewModel = new ViewModelProvider(this, appContainer.factorycena).get(CenaViewModel.class);
        mViewModel.getMalimentosfinales().observe(this.getActivity(), alimentos -> {
            mAdapter.swap(alimentos);
            // Show the repo list or the loading screen based on whether the repos data exists and is loaded
            recyclerView.setVisibility(View.VISIBLE);
        });

        recyclerView2 = (RecyclerView) root.findViewById(R.id.escogidoscena);
        assert (recyclerView2) != null;
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        mAdapter2 = new AdapterBaseDatos(getActivity().getApplicationContext(), this::onListInteractionBD);

        recyclerView2.setAdapter(mAdapter2);

        mViewModel.setfecha(fechas.getf1(),fechas.getf2());
        mViewModel.getMalimentoscena().observe(this.getActivity(), alimentoscomida -> {
            mAdapter2.swap(alimentoscomida);
        });


        mViewModel.getCaloriascena().observe(this.getActivity(), calorias -> {
            if (calorias != null) {
                text.setText(calorias.toString());
            } else {
                text.setText("0 ");
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
        Comida.Tipo tipo = Comida.Tipo.valueOf("cena");
        Comida comida = new Comida(tipo, mDate);
        Toast toast1 =
                Toast.makeText(getActivity().getApplicationContext(),
                        "Se ha añadido "+nombre, Toast.LENGTH_SHORT);

        toast1.show();

        mViewModel = new ViewModelProvider(this, appContainer.factorycena).get(CenaViewModel.class);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                long  id_alim=  mViewModel.insertarAlimento(getActivity(),aliment);
                long id_comida= mViewModel.insertarComida(getActivity(),comida);
                AlimentoEnComida alimencomida = new AlimentoEnComida(id_alim, id_comida);
                mViewModel.insertarAlimentoenComida(getActivity(),alimencomida);
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
    public void onListInteractionBD(long alim,String nombre) {

        mViewModel = new ViewModelProvider(this, appContainer.factorycena).get(CenaViewModel.class);
        Toast toast1 =
                Toast.makeText(getActivity().getApplicationContext(),
                        "Se ha eliminado "+nombre, Toast.LENGTH_SHORT);

        toast1.show();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Alimento alimento= mViewModel.obteneralimento(getActivity(),alim);
                mViewModel.eliminarAlimento(getActivity(),alimento);
                long idcomida=  mViewModel.obteneridComida(getActivity(),alim);
                List<Comida> comidas =  mViewModel.obtenercomidas(getActivity(),idcomida);
                List<AlimentoEnComida> alimencomidas=   mViewModel.obtenerAlimentosEnComida(getActivity(),alim,idcomida);
                mViewModel.eliminarcomidas(getActivity(),comidas);
                mViewModel.borraralimentosEncomida(getActivity(),alimencomidas);



            }
        });



    }


    @Override
    public void onListInteraction2(AlimentosFinales alim) {

    }
}