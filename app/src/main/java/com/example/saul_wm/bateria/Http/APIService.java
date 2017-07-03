package com.example.saul_wm.bateria.Http;

import com.example.saul_wm.bateria.Modelo.LocalizacionJSON;
import com.example.saul_wm.bateria.Modelo.UbicacionJSON;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Saul-WM on 28/06/2017.
 */

public interface APIService {
    @POST("/ubicacion.php")
    @FormUrlEncoded
    Call<UbicacionJSON> savePost(@Field("idDispositivo") String title,
                                 @Field("Localizacion") LocalizacionJSON localizacion);
}
