package com.example.fithealth.ui.desayuno;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.model.AlimentosFinales;

import java.util.List;

public class HomeViewModel extends ViewModel  {

  private final AlimentoRepository malimentorepository;
  private final LiveData<List<AlimentosFinales>> malimentosfinales;

    public HomeViewModel(AlimentoRepository alimrepositorio) {
      malimentorepository=alimrepositorio;
       malimentosfinales=malimentorepository.getcurrentalimentos();
    }

    public LiveData<List<AlimentosFinales>> getMalimentosfinales() {
        return malimentosfinales;
    }
}