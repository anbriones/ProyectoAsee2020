

package com.example.fithealth;
import android.annotation.SuppressLint;
import android.content.Intent;

        import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
       import android.widget.ImageButton;
        import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.preference.PreferenceFragmentCompat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class Usuario extends AppCompatActivity  {

    public static int RESULT_LOAD_IMAGE = 1;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajustesusuario);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
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
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/");
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            //imageView.setMaxWidth(300);
            //imageView.setMaxWidth(300);

            imageView.setImageURI(selectedImage);

        }
    }

  public static class SettingsFragment extends PreferenceFragmentCompat {
                //
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

   }
}