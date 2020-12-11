package com.example.fithealth;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fithealth.datos.model.AlimentosFinales;

public class detallesAlimento extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_alimento);
        this.setTitle(R.string.title_detalle);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        AlimentosFinales alimento = (AlimentosFinales) getIntent().getSerializableExtra("alimentos");

        TextView text = findViewById(R.id.Detallesnombre);
        text.setText("Detalles de: " + alimento.getNombreprod().toString());
        TextView cantidad = findViewById(R.id.cantidaddetalle);
        cantidad.setText(alimento.getCantidad().toString() + alimento.getUnidad().toString());
        TextView text2 = findViewById(R.id.caloriasdetalle);
        text2.setText(alimento.getCalorias().toString());
        TextView proteina = findViewById(R.id.proteinasdetalle);
        proteina.setText(alimento.getProteinas().toString());
        TextView hidratos = findViewById(R.id.hidratosdetalle);
        hidratos.setText(alimento.getHidratos().toString());
        TextView tipo = findViewById(R.id.tipodetalle);
        tipo.setText(alimento.getTipo());
        image = findViewById(R.id.imagendetalle);

        Glide.with(this).load(alimento.getUrlimagen()).into(image);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
        }
