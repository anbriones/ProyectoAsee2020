package com.example.fithealth;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.fithealth.datos.model.Alimento;
import com.example.fithealth.datos.model.AlimentoEnComida;
import com.example.fithealth.datos.model.Comida;
import com.example.fithealth.datos.roomdatabase.Comidasdatabase;
import com.example.fithealth.datos.roomdatabase.DaoAlimento;
import com.example.fithealth.datos.roomdatabase.DaoAlimentosEnComida;
import com.example.fithealth.datos.roomdatabase.DaoComidas;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ComidasDatabaseTest {
    private DaoAlimento daoAlimento;
    private DaoComidas daoComidas;
    private DaoAlimentosEnComida daoAlimentosEnComida;
    private Comidasdatabase db;


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, Comidasdatabase.class).build();
         daoAlimento= db.daoAlim();
         daoComidas=db.daoComidas();
         daoAlimentosEnComida=db.daoAlimentosEnComida();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeAlimentoAndReadAlimento() throws Exception {
        Alimento alim =new Alimento();
        alim.setId(1);
        alim.setNombre("macarrones");
       long id= daoAlimento.addalimento(alim);
        Alimento alimentos = daoAlimento.obteneralimento((long) 1);
            assertEquals(alimentos.getNombre(), "macarrones");
            assertEquals(alimentos.getId(),id);
    }

    @Test
    public void DebeEliminarAlimento() throws Exception {
        Alimento alim =new Alimento();
        alim.setId(1);
        alim.setNombre("macarrones");
        daoAlimento.addalimento(alim);
        List<Alimento> alimentos=daoAlimento.totalalimentos();
        assertEquals(alimentos.size(), 1);
       daoAlimento.deletealimento(alim);
        List<Alimento> alimentos2=daoAlimento.totalalimentos();
        Assert.assertTrue(alimentos2.isEmpty());
    }


    @Test
    public void DebeaniadirComida() throws Exception {
        String tipo="Desayuno";
        Comida comida=new Comida(Comida.Tipo.desayuno, Calendar.getInstance().getTime());
        comida.setId(1);
        long id= daoComidas.addcomida(comida);
       List<Comida> comidas = daoComidas.obtenercomidas(id);
        assertEquals(comidas.get(0).getId(), 1);

    }

    @Test
    public void DebeEliminarComida() throws Exception {
        String tipo="Desayuno";
        Comida comida=new Comida(Comida.Tipo.desayuno, Calendar.getInstance().getTime());
        comida.setId(1);
        long id= daoComidas.addcomida(comida);
        List<Comida> comidas = daoComidas.obtenercomidas(id);
        assertEquals(comidas.size(), 1);
        daoComidas.deletecomida(comida);
        List<Comida> comidas2=daoComidas.obtenercomidas(id);
        Assert.assertTrue(comidas2.isEmpty());
    }

    @Test
    public void DebeaniadirAlimentoEnComida() throws Exception {
        long id_alim=1;
        long id_comida=1;
        AlimentoEnComida alimcomida=new AlimentoEnComida(id_alim,id_comida);
        alimcomida.setId(1);
       daoAlimentosEnComida.addalimcomida(alimcomida);
        List<AlimentoEnComida> alimentoEnComidas = daoAlimentosEnComida.obteneralimentos(id_alim,id_comida);
        assertEquals(alimentoEnComidas.get(0).getId(), 1);

    }

    @Test
    public void DebeEliminarAlimentoEnComida() throws Exception {
        long id_alim=1;
        long id_comida=1;
        AlimentoEnComida alimcomida=new AlimentoEnComida(id_alim,id_comida);
        alimcomida.setId(1);
        daoAlimentosEnComida.addalimcomida(alimcomida);
        List<AlimentoEnComida> alimentoEnComidas = daoAlimentosEnComida.obteneralimentos(id_alim,id_comida);
        assertEquals(alimentoEnComidas.get(0).getId(), 1);
        daoAlimentosEnComida.deletealimentoencomida(alimcomida);
        List<AlimentoEnComida> alimentosEncomida=daoAlimentosEnComida.obteneralimentos(id_alim,id_comida);
        Assert.assertTrue(alimentosEncomida.isEmpty());
    }

}
