package com.example.fithealth.ui.cena;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.model.AlimentosFinales;

import java.util.List;

public class CenaViewModel extends ViewModel {

    private final AlimentoRepository malimentorepository;
    private final LiveData<List<AlimentosFinales>> malimentosfinales;

    public CenaViewModel(AlimentoRepository alimrepositorio) {
        malimentorepository=alimrepositorio;
        malimentosfinales=malimentorepository.getcurrentalimentos();
    }

    public LiveData<List<AlimentosFinales>> getMalimentosfinales() {
        return malimentosfinales;
    }
}