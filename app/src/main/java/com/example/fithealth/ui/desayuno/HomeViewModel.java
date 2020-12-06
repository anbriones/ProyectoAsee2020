package com.example.fithealth.ui.desayuno;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.model.Alimento;
import com.example.fithealth.datos.model.AlimentosFinales;

import java.util.List;

public class HomeViewModel extends ViewModel  {

  private final AlimentoRepository malimentorepository;
  private final LiveData<List<AlimentosFinales>> malimentosfinales;
  private final LiveData<List<Alimento>> malimentos;
  private final LiveData<Integer> caloriastotales;
    private  long f1;
    private  long f2;

    public HomeViewModel(AlimentoRepository alimrepositorio) {
      malimentorepository=alimrepositorio;

        malimentosfinales=malimentorepository.getcurrentalimentos();
       malimentos=malimentorepository.getcurrentalimentosconsumidosdesayuno();
       caloriastotales=malimentorepository.getcaloriastotalescomidas();
    }

    public void setfecha(long f1,long f2){
        this.f1=f1;
        this.f2=f2;
        malimentorepository.setFechas(f1,f2);
    }

    public LiveData<List<AlimentosFinales>> getMalimentosfinales() {
        return malimentosfinales;
    }

    public LiveData<List<Alimento>> getMalimentos() {
        return malimentos;
    }

    public LiveData<Integer> getMcalorias() {
        return caloriastotales;
    }

}