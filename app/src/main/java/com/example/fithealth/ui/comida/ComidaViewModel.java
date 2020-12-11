package com.example.fithealth.ui.comida;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.model.Alimento;
import com.example.fithealth.datos.model.AlimentosFinales;
import com.example.fithealth.datos.model.Comida;

import java.util.List;

public class ComidaViewModel extends ViewModel {

    private final AlimentoRepository malimentorepository;
    private final LiveData<List<AlimentosFinales>> malimentosfinales;
    private final LiveData<List<Alimento>> malimentoscomida;
    private final LiveData<Integer> caloriastotales;

    public ComidaViewModel(AlimentoRepository alimrepositorio) {
        malimentorepository=alimrepositorio;
        malimentosfinales=malimentorepository.getcurrentalimentos();
        malimentoscomida=malimentorepository.getcurrentalimentosconsumidos("comida");
        caloriastotales=malimentorepository.getcaloriastotalescomidas("comida");
    }
    public void setfecha(long f1,long f2){
             malimentorepository.setFechas(f1,f2);
    }
    public LiveData<List<AlimentosFinales>> getMalimentosfinales() {
        return malimentosfinales;
    }
    public LiveData<List<Alimento>> getMalimentos() {
        return malimentoscomida;
    }

    public LiveData<Integer> getMcalorias() {
        return caloriastotales;
    }

    public void insertarAlimentos(Alimento aliment, Comida comida){
        malimentorepository.insertaralimentos(aliment,comida);
    }


   public void eliminarAlimentos(long id_alim){
        malimentorepository.eliminaralimentos(id_alim);
   }

}