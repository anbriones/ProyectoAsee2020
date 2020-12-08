package com.example.fithealth.ui.comida;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.model.Alimento;
import com.example.fithealth.datos.model.AlimentoEnComida;
import com.example.fithealth.datos.model.AlimentosFinales;
import com.example.fithealth.datos.model.Comida;

import java.util.List;

public class ComidaViewModel extends ViewModel {

    private final AlimentoRepository malimentorepository;
    private final LiveData<List<AlimentosFinales>> malimentosfinales;
    private final LiveData<List<Alimento>> malimentoscomida;
    private final LiveData<Integer> caloriastotales;
private long f1;
private long f2;
    public ComidaViewModel(AlimentoRepository alimrepositorio) {
        malimentorepository=alimrepositorio;
        malimentosfinales=malimentorepository.getcurrentalimentos();
        malimentoscomida=malimentorepository.getcurrentalimentosconsumidoscomida();
        caloriastotales=malimentorepository.getcaloriastotalescomidascomida();
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
        return malimentoscomida;
    }

    public LiveData<Integer> getMcalorias() {
        return caloriastotales;
    }
    public long   insertarAlimento(Context context, Alimento alim){
        return malimentorepository.insertarAlimento(alim);
    }

    public long   insertarComida(Context context, Comida comida){
        return malimentorepository.insertarComida(comida);
    }
    public void   insertarAlimentoenComida(Context context, AlimentoEnComida alimcomida){
        malimentorepository.insertarAlimentoenComida(alimcomida);
    }

    public Alimento  obteneralimento(Context context,long idAlimento){
        return  malimentorepository.obteneralimento(idAlimento);
    }

    public void eliminarAlimento(Context context,Alimento alim ){
        malimentorepository.eliminarAlimento(alim);
    }

    public long obteneridComida(Context context,long id_alim){
        return malimentorepository.obteneridComida(id_alim);
    }
    public List<Comida> obtenercomidas(Context context,long idcomidas){//busco las comidas que estan en alimentosencomida
        return malimentorepository.obtenercomidas(idcomidas);
    }
    public void eliminarcomidas(Context context,List<Comida> listcomidas){
        malimentorepository.eliminarcomidas(listcomidas);
    }

    public List<AlimentoEnComida> obtenerAlimentosEnComida(Context context,long id_alim,long id_comida){
        return malimentorepository.obtenerAlimentosEnComida(id_alim,id_comida);
    }
    public void borraralimentosEncomida(Context context,List<AlimentoEnComida> alimentosencomida){
        malimentorepository.borraralimentosEncomida(alimentosencomida);
    }

}