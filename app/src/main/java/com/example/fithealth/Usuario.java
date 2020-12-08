

package com.example.fithealth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Usuario extends AppCompatActivity  {

    public static int RESULT_LOAD_IMAGE = 1;
    ImageView imageView;
    public static final String KEY_PREF_ALTURA = "alturak";
    public static final String KEY_PREF_PESO = "pesok";
    public static final String KEY_PREF_SEXO = "Sexok";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        setContentView(R.layout.ajustesusuario);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new ajustesusuario())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        imageView = (ImageView) findViewById(R.id.vistaimagen);

        ImageButton buttonLoadImage = findViewById(R.id.botonimagen);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        RESULT_LOAD_IMAGE);
                  }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);

            try {
                createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageView.buildDrawingCache();
            Bitmap bm = imageView.getDrawingCache();

            MediaStore.Images.Media.insertImage(getContentResolver(), bm, "Imagen user", "imagen del usuario ");

        }
        else{
            Toast toast1 =
                    Toast.makeText(this,
                            "estoy en user", Toast.LENGTH_SHORT);

            toast1.show();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Save a file: path for use with ACTION_VIEW intents
        String currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onResume() {
        super.onResume();


    }

}