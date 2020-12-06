package com.example.fithealth.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FechasActual {
    private long f1;
    private long f2;

    public FechasActual(){
        f1=fechaactual();
        f2=fechaactualmas1();
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

    public long getf1() {
        return f1;
    }
    public void setF1(long f1) {
        this.f1 = f1;
    }
    public long getf2() {
        return f2;
    }
    public void setF2(long f2) {
        this.f2 = f2;
    }
}
