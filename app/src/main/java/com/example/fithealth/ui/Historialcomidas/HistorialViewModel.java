package com.example.fithealth.ui.Historialcomidas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fithealth.datos.AlimentoRepository;
import com.example.fithealth.datos.model.Alimento;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HistorialViewModel extends ViewModel {
    private final AlimentoRepository malimentorepository;
    private final LiveData<List<Alimento>> malimentosfinales;
    public final LiveData<Integer> calorias;
    private long f1=fechaactual();
    private long f2=fechaactualmas1();



    public HistorialViewModel(AlimentoRepository alimrepositorio) {
        malimentorepository=alimrepositorio;
        malimentosfinales=malimentorepository.getcurrentalimentosconsumidos();
        calorias=malimentorepository.getcaloriastotales();
    }

    public void setfecha(long f1,long f2){
            this.f1=f1;
            this.f2=f2;
            malimentorepository.setFechas(f1,f2);
    }

    public LiveData<List<Alimento>> getMalimentosfinales() {
        return malimentosfinales;
    }

    public LiveData<Integer> getCalorias() {
        return calorias;
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
}