package com.example.fithealth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fithealth.datos.model.AlimentosFinales;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class detallesAlimento extends AppCompatActivity {
    Bitmap bmImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_alimento);

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
        tipo.setText(alimento.getTipo().toString());
        ImageView image = findViewById(R.id.imagendetalle);
//Glide.with(imagendetalle.getContext()).load(alimento.getUrlimagen()).into(image);
       // Glide.with(this).load(alimento.getUrlimagen()).into(image);
     //   downloadfile(alimento.getUrlimagen(), image);


    }

    public void downloadfile(String fileurl, ImageView img) {
        URL myfileurl = null;
        try {
            myfileurl = new URL(fileurl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URL finalMyfileurl = myfileurl;
        Runnable runable = new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) finalMyfileurl.openConnection();
                    conn.setDoInput(true);

                    conn.connect();

                    int length = conn.getContentLength();
                    int[] bitmapData = new int[length];
                    byte[] bitmapData2 = new byte[length];
                    InputStream is = conn.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    bmImg = BitmapFactory.decodeStream(is, null, options);

                  img.setImageBitmap(bmImg);

                } catch (IOException e) {
                    e.printStackTrace();
        Toast.makeText(detallesAlimento.this, "Connection Problem. Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }


    public static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 200;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
        }
