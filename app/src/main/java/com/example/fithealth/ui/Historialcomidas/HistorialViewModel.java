package com.example.fithealth.ui.Historialcomidas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.model.Alimento;

import java.util.List;

public class HistorialViewModel extends ViewModel {
    private final AlimentoRepository malimentorepository;
    private final LiveData<List<Alimento>> malimentosfinales;
    public final LiveData<Integer> calorias;



    public HistorialViewModel(AlimentoRepository alimrepositorio) {
        malimentorepository = alimrepositorio;
        malimentosfinales = malimentorepository.getcurrentalimentosconsumidos();
        calorias = malimentorepository.getcaloriastotales();
    }

    public void setfecha(long f1, long f2) {
        malimentorepository.setFechas(f1, f2);
    }

    public LiveData<List<Alimento>> getMalimentosfinales() {
        return malimentosfinales;
    }

    public LiveData<Integer> getCalorias() {
        return calorias;
    }

}