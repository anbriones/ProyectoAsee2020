package com.example.fithealth.ui.desayuno;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.model.Alimento;
import com.example.fithealth.datos.model.AlimentoEnComida;
import com.example.fithealth.datos.model.AlimentosFinales;
import com.example.fithealth.datos.model.Comida;

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

    public long   insertarAlimento( Alimento alim){
        return malimentorepository.insertarAlimento(alim);
    }

    public long   insertarComida( Comida comida){
        return malimentorepository.insertarComida(comida);
    }
    public void   insertarAlimentoenComida(AlimentoEnComida alimcomida){
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


