package com.example.fithealth.ui.lecturaAPI;


import androidx.room.Query;

import com.example.fithealth.lecturaJson.AlimentosFinales;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface AlimentosService {



    @GET("uc?export=download&id=1Rm0bD36EfCBVBbLjhhI80IVx1eRAtRi0")
       Call<List<AlimentosFinales>> getfoods();


}
