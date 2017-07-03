package com.example.saul_wm.bateria.Utils;


import com.example.saul_wm.bateria.Http.APIService;
import com.example.saul_wm.bateria.Http.RetrofitCliente;

public final class Constantes {
    public static final int BATERIA_MINIMA = 20; //Valores posibles: 0-100
    public static final long INTERVALO_MUESTRA_ACELERACION = 1000; //2 segundos
    public static final long INTERVALO_MUESTRA_BRUJULA = 1000;
    public static final long TIEMPO_MIN_ACTUALIZACION_GPS = 30000;
    public static final long DISTANCIA_MIN_ACTUALIZACION_GPS = 10;

    public static final int VERSION_BD = 3;
    public static final String NOMBRE_BD = "Base_Bateria";

    public static final int REQUEST_LOCATION = 1;
    public static final long INTERVALO_LOCALIZACION = 10000;
    public static final long INTERVALO_LOCALIZACION_RAPIDA = INTERVALO_LOCALIZACION / 2;
    public static final float MINIMA_DISTANCIA_ENTRE_ACTUALIZACIONES = 1;

    public static final int REQUEST_CHECK_SETTINGS = 0x1;

    public static final String BASE_URL = "";

    public static APIService getAPIService() {
        return RetrofitCliente.getClient(BASE_URL).create(APIService.class);
    }
    float SHAKE_THRESHOLD = (float) 6.5E-8;
}
