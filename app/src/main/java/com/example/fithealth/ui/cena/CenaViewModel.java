package com.example.fithealth.ui.cena;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.model.Alimento;
import com.example.fithealth.datos.model.AlimentosFinales;
import com.example.fithealth.datos.model.Comida;

import java.util.List;

public class CenaViewModel extends ViewModel {

    private final AlimentoRepository malimentorepository;
    private final LiveData<List<AlimentosFinales>> malimentosfinales;
    private final LiveData<List<Alimento>> malimentoscena;
    private final LiveData<Integer> caloriascena;


    public CenaViewModel(AlimentoRepository alimrepositorio) {
        malimentorepository=alimrepositorio;
        malimentosfinales=malimentorepository.getcurrentalimentos();
        malimentoscena=malimentorepository.getcurrentalimentosconsumidos("cena");
        caloriascena=malimentorepository.getcaloriastotalescomidas("cena");
    }
    public void setfecha(long f1,long f2){

        malimentorepository.setFechas(f1,f2);
    }
    public LiveData<List<AlimentosFinales>> getMalimentosfinales() {
        return malimentosfinales;
    }
    public LiveData<List<Alimento>> getMalimentoscena() {
        return malimentoscena;
    }
    public LiveData<Integer> getCaloriascena() {
        return caloriascena;
    }

    public void insertarAlimentos(Alimento aliment, Comida comida){
        malimentorepository.insertaralimentos(aliment,comida);
    }

    public void eliminarAlimentos(long id_alim){
        malimentorepository.eliminaralimentos(id_alim);
    }
}